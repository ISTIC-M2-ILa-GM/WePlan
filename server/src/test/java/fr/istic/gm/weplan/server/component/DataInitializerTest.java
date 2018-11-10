package fr.istic.gm.weplan.server.component;

import fr.istic.gm.weplan.domain.component.ScheduledEventGenerator;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.entities.Event;
import fr.istic.gm.weplan.domain.service.ActivityService;
import fr.istic.gm.weplan.domain.service.CityService;
import fr.istic.gm.weplan.domain.service.DepartmentService;
import fr.istic.gm.weplan.domain.service.EventDaoService;
import fr.istic.gm.weplan.domain.service.RegionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;

import static fr.istic.gm.weplan.server.TestData.someActivity;
import static fr.istic.gm.weplan.server.TestData.someCity;
import static fr.istic.gm.weplan.server.TestData.someDepartment;
import static fr.istic.gm.weplan.server.TestData.someEvent;
import static fr.istic.gm.weplan.server.TestData.someEventDao;
import static fr.istic.gm.weplan.server.TestData.someRegion;
import static fr.istic.gm.weplan.server.TestData.someUserDao;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataInitializerTest {

    private DataInitializer dataInitializer;

    @Mock
    private CityService mockCityService;

    @Mock
    private DepartmentService mockDepartmentService;

    @Mock
    private RegionService mockRegionService;

    @Mock
    private ActivityService mockActivityService;

    @Mock
    private EventDaoService mockEventDaoService;

    @Mock
    private ScheduledEventGenerator mockScheduledEventGenerator;

    @Before
    public void setUp() {
        dataInitializer = new DataInitializer(
                mockCityService,
                mockDepartmentService,
                mockRegionService,
                mockActivityService,
                mockEventDaoService,
                mockScheduledEventGenerator
        );
    }

    @Test
    public void shouldSaveAllData() {

        doThrow(DomainException.class).when(mockRegionService).getRegion(anyString());
        doThrow(DomainException.class).when(mockDepartmentService).getDepartment(anyString());
        doThrow(DomainException.class).when(mockCityService).getCity(anyString());
        doThrow(DomainException.class).when(mockActivityService).getActivity(anyString());

        when(mockRegionService.createRegion(any())).thenReturn(someRegion());
        when(mockDepartmentService.createDepartment(any())).thenReturn(someDepartment());
        when(mockCityService.createCity(any())).thenReturn(someCity());

        dataInitializer.onApplicationEvent(null);

        verify(mockRegionService, times(2)).createRegion(any());
        verify(mockDepartmentService, times(5)).createDepartment(any());
        verify(mockCityService, times(7)).createCity(any());
        verify(mockActivityService, times(5)).createActivity(any());
        verify(mockScheduledEventGenerator).checkWeatherThenGenerateEvents();

    }

    @Test
    public void shouldNotSaveDataWhenTheyAreAlreadyHere() {

        when(mockRegionService.getRegion(anyString())).thenReturn(someRegion());
        when(mockDepartmentService.getDepartment(anyString())).thenReturn(someDepartment());
        when(mockCityService.getCity(anyString())).thenReturn(someCity());
        when(mockActivityService.getActivity(anyString())).thenReturn(someActivity());
        Page<Event> page = new PageImpl<>(Collections.singletonList(someEventDao()));
        when(mockEventDaoService.getEventsDao(any())).thenReturn(page);

        dataInitializer.onApplicationEvent(null);

        verify(mockRegionService, never()).createRegion(any());
        verify(mockDepartmentService, never()).createDepartment(any());
        verify(mockCityService, never()).createCity(any());
        verify(mockActivityService, never()).createActivity(any());
        verify(mockScheduledEventGenerator, never()).checkWeatherThenGenerateEvents();

    }
}
