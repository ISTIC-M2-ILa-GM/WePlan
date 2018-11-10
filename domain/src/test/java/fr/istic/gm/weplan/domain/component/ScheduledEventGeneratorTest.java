package fr.istic.gm.weplan.domain.component;

import fr.istic.gm.weplan.domain.adapter.EventBrokerAdapter;
import fr.istic.gm.weplan.domain.adapter.WeatherAdapter;
import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.weather.Week;
import fr.istic.gm.weplan.domain.service.ActivityDaoService;
import fr.istic.gm.weplan.domain.service.EventService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static fr.istic.gm.weplan.domain.TestData.someActivity;
import static fr.istic.gm.weplan.domain.TestData.someWeek;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ScheduledEventGeneratorTest {
    @Mock
    private WeatherAdapter mockWeatherAdapter;

    @Mock
    private EventService mockEventService;

    @Mock
    private ActivityDaoService mockActivityDaoService;

    @Mock
    private EventBrokerAdapter mockEventBrokerAdapter;

    private ScheduledEventGenerator scheduledEventGenerator;

    @Before
    public void setUp() {
        this.scheduledEventGenerator = new ScheduledEventGenerator(mockWeatherAdapter, mockEventService, mockActivityDaoService, mockEventBrokerAdapter);
    }

    @Test
    public void checkWeatherThenGenerateEvents() {
        Activity activity = someActivity();
        Week week = someWeek();
        List<Activity> someActivities = Arrays.asList(activity, someActivity());

        when(this.mockActivityDaoService.getActivitiesDao()).thenReturn(someActivities);
        when(this.mockWeatherAdapter.getWeatherWeek(anyInt())).thenReturn(week);

        this.scheduledEventGenerator.checkWeatherThenGenerateEvents();

        verify(this.mockActivityDaoService).getActivitiesDao();

        verify(this.mockEventService, times(10)).createEvent(any());

        verify(this.mockEventBrokerAdapter, times(10)).sendEvent(any());
    }
}