package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.EventAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.entities.Event;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.service.EventDaoService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.TestData.ID;
import static fr.istic.gm.weplan.domain.TestData.someEvent;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.PageRequest.of;

@RunWith(MockitoJUnitRunner.class)
public class EventDaoServiceTest {

    private EventDaoService service;

    @Mock
    private EventAdapter mockEventAdapter;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Instant now;

    @Before
    public void setUp() {
        service = new EventDaoServiceImpl(mockEventAdapter);

        now = Instant.now();
    }

    @Test
    public void shouldGetEventsDao() {

        PageRequest pageRequest = PageRequest.builder().page(0).size(10).build();
        Event event = someEvent();

        when(mockEventAdapter.findAll(any())).thenReturn(new PageImpl<>(Collections.singletonList(event)));

        Page<Event> result = service.getEventsDao(pageRequest);

        verify(mockEventAdapter).findAll(of(pageRequest.getPage(), pageRequest.getSize()));

        assertThat(result, notNullValue());
        assertThat(result, equalTo(event));
    }

    @Test
    public void shouldGetEventDao() {

        Event event = someEvent();
        Optional<Event> optionalEvent = Optional.of(event);

        when(mockEventAdapter.findById(any())).thenReturn(optionalEvent);

        Event result = service.getEventDao(ID);

        verify(mockEventAdapter).findById(ID);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(event));
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetANullDepartmentDao() {

        Optional<Event> optionalEvent = Optional.empty();

        when(mockEventAdapter.findById(any())).thenReturn(optionalEvent);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Event.class.getSimpleName()));

        service.getEventDao(ID);
    }
}
