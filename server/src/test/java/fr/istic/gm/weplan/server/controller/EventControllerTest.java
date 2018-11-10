package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.service.EventService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static fr.istic.gm.weplan.server.TestData.ID;
import static fr.istic.gm.weplan.server.TestData.someEvent;
import static fr.istic.gm.weplan.server.TestData.somePageEvents;
import static fr.istic.gm.weplan.server.TestData.somePageOptions;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {

    private EventController controller;

    @Mock
    private EventService mockEventService;

    @Before
    public void setUp() {
        controller = new EventController(mockEventService);
    }

    @Test
    public void shouldGetEvents() {

        PageRequest pageRequest = somePageOptions();
        PageDto<EventDto> pageEvents = somePageEvents();

        when(mockEventService.getEvents(any())).thenReturn(pageEvents);

        PageDto<EventDto> result = controller.getEvents(pageRequest);

        verify(mockEventService).getEvents(pageRequest);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(pageEvents));
    }

    @Test
    public void shouldGetAEvent() {

        EventDto event = someEvent();

        when(mockEventService.getEvent(any())).thenReturn(event);

        EventDto result = controller.getEvent(ID);

        verify(mockEventService).getEvent(ID);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(event));
    }
}
