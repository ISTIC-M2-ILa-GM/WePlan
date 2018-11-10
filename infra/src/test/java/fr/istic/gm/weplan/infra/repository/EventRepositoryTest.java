package fr.istic.gm.weplan.infra.repository;

import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Event;
import fr.istic.gm.weplan.infra.TestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static fr.istic.gm.weplan.infra.TestData.someActivity;
import static fr.istic.gm.weplan.infra.TestData.someCity;
import static fr.istic.gm.weplan.infra.TestData.someEvent;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfiguration.class})
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ActivityRepository activityRepository;

    private Event entity1;
    private Event entity2;
    private City city;
    private Activity activity;

    @Before
    public void setUp() {

        eventRepository.deleteAll();
        cityRepository.deleteAll();
        activityRepository.deleteAll();

        activity = someActivity();
        activity.setCities(null);
        activity.setId(null);
        city = someCity();
        city.setDepartment(null);
        city.setId(null);

        entity1 = someEvent();
        entity1.setCity(city);
        entity1.setActivity(activity);
        entity2 = someEvent();
        entity2.setCity(city);
        entity2.setActivity(activity);

        city = cityRepository.save(city);
        activity = activityRepository.save(activity);
        entity1 = eventRepository.save(entity1);
        entity2 = eventRepository.save(entity2);
    }

    @Test
    public void shouldFindAll() {

        Page<Event> events = eventRepository.findAll(PageRequest.of(0, 10));

        assertThat(events, notNullValue());
        assertThat(events.getTotalPages(), equalTo(1));
        assertThat(events.getContent(), hasSize(2));
        assertThat(events.getSize(), equalTo(10));
    }

    @Test
    public void shouldFindAllByDeletedAtIsNull() {

        entity1.setDeletedAt(Instant.now());
        entity1 = eventRepository.save(entity1);

        Page<Event> events = eventRepository.findAllByCitiesAndActivities(PageRequest.of(0, 10), singletonList(city), singletonList(activity));

        assertThat(events, notNullValue());
        assertThat(events.getTotalPages(), equalTo(1));
        assertThat(events.getContent(), hasSize(1));
        assertThat(events.getSize(), equalTo(10));
    }

    @Test
    public void shouldNotFindAllByAfterDate() {

        entity1.setDate(Instant.now().minus(2, ChronoUnit.DAYS));
        entity1 = eventRepository.save(entity1);

        Page<Event> events = eventRepository.findAllByCitiesAndActivities(PageRequest.of(0, 10), singletonList(city), singletonList(activity));

        assertThat(events, notNullValue());
        assertThat(events.getTotalPages(), equalTo(1));
        assertThat(events.getContent(), hasSize(1));
        assertThat(events.getSize(), equalTo(10));
    }

    @Test
    public void shouldFindAllDateAsc() {

        entity1.setDate(Instant.now().plus(3, ChronoUnit.DAYS));
        entity1 = eventRepository.save(entity1);

        Page<Event> events = eventRepository.findAllByCitiesAndActivities(PageRequest.of(0, 10), singletonList(city), singletonList(activity));

        assertThat(events, notNullValue());
        assertThat(events.getTotalPages(), equalTo(1));
        assertThat(events.getContent(), hasSize(2));
        assertThat(events.getContent().get(0).getId(), equalTo(entity2.getId()));
        assertThat(events.getContent().get(1).getId(), equalTo(entity1.getId()));
        assertThat(events.getSize(), equalTo(10));
    }

    @Test
    public void shouldFindAllByCitiesAndActivity() {

        entity1.setCity(null);
        entity1.setActivity(null);
        entity1 = eventRepository.save(entity1);

        Page<Event> events = eventRepository.findAllByCitiesAndActivities(PageRequest.of(0, 10), singletonList(city), singletonList(activity));

        assertThat(events, notNullValue());
        assertThat(events.getTotalPages(), equalTo(1));
        assertThat(events.getContent(), hasSize(1));
        assertThat(events.getContent().get(0).getId(), equalTo(entity2.getId()));
        assertThat(events.getSize(), equalTo(10));
    }

    @Test
    public void shouldFindAllWithPage() {

        Page<Event> events = eventRepository.findAll(PageRequest.of(0, 1));

        assertThat(events, notNullValue());
        assertThat(events.getTotalPages(), equalTo(2));
        assertThat(events.getContent(), hasSize(1));
        assertThat(events.getSize(), equalTo(1));
    }

    @Test
    public void shouldGetOneEvent() {

        Optional<Event> event = eventRepository.findById(entity1.getId());

        assertThat(event, notNullValue());
        assertThat(event.isPresent(), equalTo(true));
        assertThat(event.get().getId(), equalTo(entity1.getId()));
        assertThat(event.get().getActivity().getId(), equalTo(entity1.getActivity().getId()));
        assertThat(event.get().getCity().getId(), equalTo(entity1.getCity().getId()));
        assertThat(event.get().getId(), equalTo(entity1.getId()));
    }

    @Test
    public void shouldCreateAEvent() {

        Event event = someEvent();
        event.setId(null);
        event.setCreatedAt(null);
        event.setUpdatedAt(null);
        event.setCity(null);
        event.setActivity(null);

        event = eventRepository.save(event);

        assertThat(event, notNullValue());
        assertThat(event.getId(), notNullValue());
        assertThat(event.getCreatedAt(), notNullValue());
        assertThat(event.getUpdatedAt(), notNullValue());
    }
}
