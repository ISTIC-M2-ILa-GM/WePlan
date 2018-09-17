package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.service.CityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static fr.istic.gm.weplan.server.TestData.ID;
import static fr.istic.gm.weplan.server.TestData.someCity;
import static fr.istic.gm.weplan.server.TestData.somePageCities;
import static fr.istic.gm.weplan.server.TestData.somePageOptions;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityControllerTest {

    private CityController controller;

    @Mock
    private CityService mockCityService;

    @Before
    public void setUp() {
        controller = new CityController(mockCityService);
    }

    @Test
    public void shouldGetCities() {

        PageOptions pageOptions = somePageOptions();
        PageDto<CityDto> pageCities = somePageCities();

        when(mockCityService.getCities(any())).thenReturn(pageCities);

        PageDto<CityDto> result = controller.getCities(pageOptions);

        verify(mockCityService).getCities(pageOptions);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(pageCities));
    }

    @Test
    public void shouldGetACity() {

        CityDto city = someCity();

        when(mockCityService.getCity(any())).thenReturn(city);

        CityDto result = controller.getCity(ID);

        verify(mockCityService).getCity(ID);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(city));
    }
}
