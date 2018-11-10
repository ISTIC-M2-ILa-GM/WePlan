package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.service.CityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static fr.istic.gm.weplan.server.TestData.ID;
import static fr.istic.gm.weplan.server.TestData.someCity;
import static fr.istic.gm.weplan.server.TestData.someCityRequest;
import static fr.istic.gm.weplan.server.TestData.somePageCities;
import static fr.istic.gm.weplan.server.TestData.somePageOptions;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
    public void shouldGetCitiesPage() {

        PageRequest pageRequest = somePageOptions();
        PageDto<CityDto> pageCities = somePageCities();

        when(mockCityService.getCities(any())).thenReturn(pageCities);

        PageDto<CityDto> result = controller.getCities(pageRequest);

        verify(mockCityService).getCities(pageRequest);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(pageCities));
    }

    @Test
    public void shouldGetACity() {

        CityDto city = someCity();

        when(mockCityService.getCity(anyLong())).thenReturn(city);

        CityDto result = controller.getCity(ID);

        verify(mockCityService).getCity(ID);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(city));
    }

    @Test
    public void shouldCreateACity() {

        CityRequest cityRequest = someCityRequest();
        CityDto cityDto = someCity();

        when(mockCityService.createCity(any())).thenReturn(cityDto);

        CityDto result = controller.createCity(cityRequest);

        verify(mockCityService).createCity(cityRequest);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(cityDto));
    }

    @Test
    public void shouldDeleteACity() {

        controller.deleteCity(ID);

        verify(mockCityService).deleteCity(ID);
    }

    @Test
    public void shouldPatchACity() {

        Map<String, Object> patch = new HashMap<>();

        CityDto cityDto = someCity();

        when(mockCityService.patchCity(any(), any())).thenReturn(cityDto);

        CityDto result = controller.patchCity(ID, patch);

        verify(mockCityService).patchCity(ID, patch);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(cityDto));
    }
}
