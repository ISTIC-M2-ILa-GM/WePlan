package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.AuthAdapter;
import fr.istic.gm.weplan.domain.adapter.EventAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.entities.Event;
import fr.istic.gm.weplan.domain.model.entities.User;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.service.EventDaoService;
import fr.istic.gm.weplan.domain.service.UserDaoService;
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
import java.util.Collections;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.TestData.ID;
import static fr.istic.gm.weplan.domain.TestData.someEvent;
import static fr.istic.gm.weplan.domain.TestData.somePageOptions;
import static fr.istic.gm.weplan.domain.TestData.someUser;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.PageRequest.of;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    private EventServiceImpl service;

    @Mock
    private EventAdapter mockEventAdapter;

    @Mock
    private AuthAdapter mockAuthAdapter;

    @Mock
    private EventDaoService mockEventDaoService;

    @Mock
    private UserDaoService mockUserDaoService;

    @Mock
    private Clock mockClock;

    private PersistenceMapper persistenceMapper;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Instant now;

    @Before
    public void setUp() {
        persistenceMapper = Mappers.getMapper(PersistenceMapper.class);
        service = new EventServiceImpl(mockEventAdapter, mockEventDaoService, mockUserDaoService, mockAuthAdapter, persistenceMapper, mockClock);

        now = Instant.now();
        when(mockClock.instant()).thenReturn(now);
    }

    @Test
    public void shouldGetEvents() {

        User user = someUser();
        PageRequest pageRequest = somePageOptions();
        Page<Event> events = new PageImpl<>(Collections.singletonList(someEvent()), of(1, 1), 2);

        when(mockAuthAdapter.loggedUserId()).thenReturn(ID);
        when(mockUserDaoService.getUserDao(any())).thenReturn(user);
        when(mockEventAdapter.findAllByCitiesAndActivities(any(), any(), any())).thenReturn(events);

        PageDto<EventDto> results = service.getEvents(pageRequest);

        org.springframework.data.domain.PageRequest expectedPageable = of(pageRequest.getPage(), pageRequest.getSize());

        verify(mockAuthAdapter).loggedUserId();
        verify(mockUserDaoService).getUserDao(ID);
        verify(mockEventAdapter).findAllByCitiesAndActivities(expectedPageable, user.getCities(), user.getActivities());

        assertThat(results, notNullValue());
        assertThat(results.getResults(), equalTo(persistenceMapper.toEventsDto(events.getContent())));
        assertThat(results.getTotalPages(), equalTo(2));
        assertThat(results.getSize(), equalTo(1));
    }

    @Test
    public void shouldGetnEvent() {

        Event event = someEvent();
        Optional<Event> optionalEvent = Optional.of(event);

        when(mockEventAdapter.findById(any())).thenReturn(optionalEvent);

        EventDto results = service.getEvent(ID);

        verify(mockEventAdapter).findById(ID);

        assertThat(results, notNullValue());
        assertThat(results, equalTo(persistenceMapper.toEventDto(event)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetANullEvent() {

        Optional<Event> optionalEvent = Optional.empty();

        when(mockEventAdapter.findById(any())).thenReturn(optionalEvent);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Event.class.getSimpleName()));

        service.getEvent(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetADeletedEvent() {

        Event event = someEvent();
        event.setDeletedAt(Instant.now());
        Optional<Event> optionalEvent = Optional.of(event);

        when(mockEventAdapter.findById(any())).thenReturn(optionalEvent);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Event.class.getSimpleName()));

        service.getEvent(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetAnEventWithNullId() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Event.class.getSimpleName()));

        service.getEvent(null);
    }

    @Test
    public void shouldDeleteAnEvent() {

        Event event = someEvent();
        Optional<Event> optionalEvent = Optional.of(event);

        when(mockEventAdapter.findById(any())).thenReturn(optionalEvent);
        when(mockEventAdapter.save(any())).thenReturn(event);

        service.deleteEvent(ID);

        verify(mockEventAdapter).findById(ID);
        verify(mockEventAdapter).save(event);
        verify(mockClock).instant();

        assertThat(event.getDeletedAt(), notNullValue());
        assertThat(event.getDeletedAt(), equalTo(now));
    }

    @Test
    public void shouldThrowDomainExceptionWhenDeleteANullEvent() {

        Optional<Event> optionalEvent = Optional.empty();

        when(mockEventAdapter.findById(any())).thenReturn(optionalEvent);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Event.class.getSimpleName()));

        service.deleteEvent(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenDeleteADeletedEvent() {

        Event event = someEvent();
        event.setDeletedAt(Instant.now());
        Optional<Event> optionalEvent = Optional.of(event);

        when(mockEventAdapter.findById(any())).thenReturn(optionalEvent);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Event.class.getSimpleName()));

        service.deleteEvent(ID);
    }

    @Test
    public void shouldCreateEvent() {
        Event event = someEvent();
        when(this.mockEventAdapter.save(any())).thenReturn(event);

        EventDto result = this.service.createEvent(event);
        verify(this.mockEventAdapter).save(event);
        assertThat(result, equalTo(this.persistenceMapper.toEventDto(event)));
    }
}
