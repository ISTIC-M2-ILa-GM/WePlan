package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.ActivityAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.ActivityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.ActivityRequest;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.service.ActivityService;
import fr.istic.gm.weplan.domain.service.CityDaoService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.TestData.ID;
import static fr.istic.gm.weplan.domain.TestData.someActivity;
import static fr.istic.gm.weplan.domain.TestData.someActivityRequest;
import static fr.istic.gm.weplan.domain.TestData.someCity;
import static fr.istic.gm.weplan.domain.TestData.somePageOptions;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOTHING_TO_PATCH;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static fr.istic.gm.weplan.domain.exception.DomainException.WRONG_DATA_TO_PATCH;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceTest {

    private ActivityService service;

    @Mock
    private ActivityAdapter mockActivityAdapter;

    @Mock
    private CityDaoService mockCityService;

    @Mock
    private Clock mockClock;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private PersistenceMapper persistenceMapper;

    @Before
    public void setUp() {
        persistenceMapper = Mappers.getMapper(PersistenceMapper.class);
        service = new ActivityServiceImpl(mockActivityAdapter, mockCityService, persistenceMapper, mockClock);
    }

    @Test
    public void shouldGetActivitiesPage() {

        PageRequest pageRequest = somePageOptions();
        Page<Activity> activities = new PageImpl<>(singletonList(someActivity()), org.springframework.data.domain.PageRequest.of(1, 1), 2);

        when(mockActivityAdapter.findAllByDeletedAtIsNull(any())).thenReturn(activities);

        PageDto<ActivityDto> results = service.getActivities(pageRequest);

        org.springframework.data.domain.PageRequest expectedPageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getSize());

        verify(mockActivityAdapter).findAllByDeletedAtIsNull(expectedPageable);

        assertThat(results, notNullValue());
        assertThat(results.getResults(), equalTo(persistenceMapper.toActivitiesDto(activities.getContent())));
        assertThat(results.getTotalPages(), equalTo(2));
        assertThat(results.getSize(), equalTo(1));
    }

    @Test
    public void shouldGetActivitiesPageWithoutPage() {

        List<Activity> activities = singletonList(someActivity());

        when(mockActivityAdapter.findAllByDeletedAtIsNull()).thenReturn(activities);

        PageDto<ActivityDto> results = service.getActivities(null);

        verify(mockActivityAdapter).findAllByDeletedAtIsNull();

        assertThat(results, notNullValue());
        assertThat(results.getResults(), equalTo(persistenceMapper.toActivitiesDto(activities)));
        assertThat(results.getTotalPages(), equalTo(1));
        assertThat(results.getSize(), equalTo(1));
    }

    @Test
    public void shouldGetActivities() {

        List<Activity> activities = singletonList(someActivity());

        when(mockActivityAdapter.findAllByDeletedAtIsNull()).thenReturn(activities);

        List<ActivityDto> results = service.getActivities();

        verify(mockActivityAdapter).findAllByDeletedAtIsNull();

        assertThat(results, notNullValue());
        assertThat(results, equalTo(persistenceMapper.toActivitiesDto(activities)));
    }

    @Test
    public void shouldGetActivity() {

        Activity activity = someActivity();
        Optional<Activity> optionalActivity = Optional.of(activity);

        when(mockActivityAdapter.findById(any())).thenReturn(optionalActivity);

        ActivityDto results = service.getActivity(ID);

        verify(mockActivityAdapter).findById(ID);

        assertThat(results, notNullValue());
        assertThat(results, equalTo(persistenceMapper.toActivityDto(activity)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetANullActivity() {

        Optional<Activity> optionalActivity = Optional.empty();

        when(mockActivityAdapter.findById(any())).thenReturn(optionalActivity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Activity.class.getSimpleName()));

        service.getActivity(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetADeletedActivity() {

        Activity activity = someActivity();
        activity.setDeletedAt(Instant.now());
        Optional<Activity> optionalActivity = Optional.of(activity);

        when(mockActivityAdapter.findById(any())).thenReturn(optionalActivity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Activity.class.getSimpleName()));

        service.getActivity(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetAActivityWithNullId() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Activity.class.getSimpleName()));

        service.getActivity(null);
    }

    @Test
    public void shouldCreateAnActivity() {

        Activity activity = someActivity();
        ActivityRequest activityRequest = someActivityRequest();
        activityRequest.setCitiesId(null);

        when(mockActivityAdapter.save(any())).thenReturn(activity);

        ActivityDto results = service.createActivity(activityRequest);

        Activity expectedActivity = persistenceMapper.toActivity(activityRequest);

        verify(mockActivityAdapter).save(expectedActivity);

        assertThat(results, notNullValue());
        assertThat(results.getActivityType().toString(), equalTo(activity.getActivityType().toString()));
        assertThat(results.getCost(), equalTo(activity.getCost()));
        assertThat(results.getName(), equalTo(activity.getName()));
        assertThat(results.getCities(), equalTo(persistenceMapper.toCitiesDto(activity.getCities())));
    }

    @Test
    public void shouldCreateAnActivityWithCity() {

        Activity activity = someActivity();
        City city = someCity();
        ActivityRequest activityRequest = someActivityRequest();
        activityRequest.setCitiesId(asList(ID, ID + 1));

        when(mockActivityAdapter.save(any())).thenReturn(activity);
        when(mockCityService.getCityDao(any())).thenReturn(city);

        ActivityDto results = service.createActivity(activityRequest);

        verify(mockCityService).getCityDao(ID);
        verify(mockCityService).getCityDao(ID + 1);

        assertThat(results, notNullValue());
        assertThat(results.getCities(), notNullValue());
        assertThat(results.getCities().get(0), equalTo(persistenceMapper.toCityDto(activity.getCities().get(0))));
        assertThat(results.getName(), equalTo(activity.getName()));
        assertThat(results.getCost(), equalTo(activity.getCost()));
        assertThat(results.getActivityType().toString(), equalTo(activity.getActivityType().toString()));
    }

    @Test
    public void shouldDeleteAnActivity() {

        Instant now = Instant.now();
        Activity activity = someActivity();
        activity.setCities(null);
        Optional<Activity> optionalActivity = Optional.of(activity);

        when(mockActivityAdapter.findById(any())).thenReturn(optionalActivity);
        when(mockActivityAdapter.save(any())).thenReturn(activity);
        when(mockClock.instant()).thenReturn(now);

        service.deleteActivity(ID);

        verify(mockActivityAdapter).findById(ID);
        verify(mockActivityAdapter).save(activity);
        verify(mockClock).instant();

        assertThat(activity.getDeletedAt(), notNullValue());
        assertThat(activity.getDeletedAt(), equalTo(now));
    }

    @Test
    public void shouldThrowDomainExceptionWhenDeleteANullActivity() {

        Optional<Activity> optionalActivity = Optional.empty();

        when(mockActivityAdapter.findById(any())).thenReturn(optionalActivity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Activity.class.getSimpleName()));

        service.deleteActivity(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenDeleteADeletedActivity() {

        Activity activity = someActivity();
        activity.setDeletedAt(Instant.now());
        Optional<Activity> optionalActivity = Optional.of(activity);

        when(mockActivityAdapter.findById(any())).thenReturn(optionalActivity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Activity.class.getSimpleName()));

        service.deleteActivity(ID);
    }

    @Test
    public void shouldPatchAActivity() {

        Activity activity = someActivity();
        activity.setCities(null);
        activity.setId(ID);
        Optional<Activity> optionalActivity = Optional.of(activity);

        Map<String, Object> patch = new HashMap<>();
        patch.put("name", "a-new-name");
        patch.put("cost", 10000f);
        patch.put("id", 18);

        when(mockActivityAdapter.findById(any())).thenReturn(optionalActivity);
        when(mockActivityAdapter.save(any())).thenReturn(activity);

        ActivityDto result = service.patchActivity(ID, patch);

        verify(mockActivityAdapter).findById(ID);
        verify(mockActivityAdapter).save(activity);

        assertThat(result, notNullValue());
        assertThat(result.getId(), equalTo(ID));
        assertThat(result.getName(), equalTo("a-new-name"));
        assertThat(result.getCost(), equalTo(10000f));
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchADeletedActivity() {

        Activity activity = someActivity();
        activity.setDeletedAt(Instant.now());
        Optional<Activity> optionalActivity = Optional.of(activity);

        when(mockActivityAdapter.findById(any())).thenReturn(optionalActivity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Activity.class.getSimpleName()));

        service.patchActivity(ID, new HashMap<>());
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchANullActivity() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Activity.class.getSimpleName()));

        service.patchActivity(null, new HashMap<>());
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchAActivityWithANullPatch() {

        Activity activity = someActivity();
        activity.setDeletedAt(null);
        activity.setCities(null);
        Optional<Activity> optionalActivity = Optional.of(activity);

        when(mockActivityAdapter.findById(any())).thenReturn(optionalActivity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(NOTHING_TO_PATCH);

        service.patchActivity(ID, null);
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchAActivityWithAnEmptyPatch() {

        Activity activity = someActivity();
        activity.setDeletedAt(null);
        activity.setCities(null);
        Optional<Activity> optionalActivity = Optional.of(activity);

        when(mockActivityAdapter.findById(any())).thenReturn(optionalActivity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(NOTHING_TO_PATCH);

        service.patchActivity(ID, new HashMap<>());
    }

    @Test
    public void shouldThrowExceptionWhenPatchAActivityWithWrongType() {

        Activity activity = someActivity();
        activity.setDeletedAt(null);
        activity.setCities(null);
        Optional<Activity> optionalActivity = Optional.of(activity);

        Map<String, Object> patch = new HashMap<>();
        patch.put("name", new Object());

        when(mockActivityAdapter.findById(any())).thenReturn(optionalActivity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(WRONG_DATA_TO_PATCH);

        service.patchActivity(ID, patch);
    }
}
