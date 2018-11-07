package fr.istic.gm.weplan.domain.component;

import fr.istic.gm.weplan.domain.service.ActivityService;
import fr.istic.gm.weplan.domain.service.CityService;
import fr.istic.gm.weplan.domain.service.DepartmentService;
import fr.istic.gm.weplan.domain.service.RegionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static fr.istic.gm.weplan.domain.TestData.someCityDto;
import static fr.istic.gm.weplan.domain.TestData.someDepartmentDto;
import static fr.istic.gm.weplan.domain.TestData.someRegionDto;
import static org.mockito.ArgumentMatchers.any;
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
    private ScheduledEventGenerator mockScheduledEventGenerator;

    @Before
    public void setUp() {
        dataInitializer = new DataInitializer(
                mockCityService,
                mockDepartmentService,
                mockRegionService,
                mockActivityService,
                mockScheduledEventGenerator
        );
    }

    @Test
    public void shouldSaveAllData() {

        when(mockRegionService.createRegion(any())).thenReturn(someRegionDto());
        when(mockDepartmentService.createDepartment(any())).thenReturn(someDepartmentDto());
        when(mockCityService.createCity(any())).thenReturn(someCityDto());

        dataInitializer.onApplicationEvent(null);

        verify(mockRegionService, times(2)).createRegion(any());
        verify(mockDepartmentService, times(5)).createDepartment(any());
        verify(mockCityService, times(7)).createCity(any());
        verify(mockActivityService, times(5)).createActivity(any());
        verify(mockScheduledEventGenerator).checkWeatherThenGenerateEvents();

    }
}
