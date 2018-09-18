package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.CityAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.service.DepartmentService;
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
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.TestData.ID;
import static fr.istic.gm.weplan.domain.TestData.someCity;
import static fr.istic.gm.weplan.domain.TestData.someCityRequest;
import static fr.istic.gm.weplan.domain.TestData.someDepartment;
import static fr.istic.gm.weplan.domain.TestData.somePageOptions;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    private CityServiceImpl service;

    @Mock
    private CityAdapter mockCityAdapter;

    @Mock
    private DepartmentService mockDepartmentService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private PersistenceMapper persistenceMapper;

    @Before
    public void setUp() {
        persistenceMapper = Mappers.getMapper(PersistenceMapper.class);
        service = new CityServiceImpl(mockCityAdapter, mockDepartmentService, persistenceMapper);
    }

    @Test
    public void shouldGetCities() {

        PageOptions pageOptions = somePageOptions();
        Page<City> cities = new PageImpl<>(Collections.singletonList(someCity()), PageRequest.of(1, 1), 2);

        when(mockCityAdapter.findAll(any())).thenReturn(cities);

        PageDto<CityDto> results = service.getCities(pageOptions);

        PageRequest expectedPageable = PageRequest.of(pageOptions.getPage(), pageOptions.getSize());

        verify(mockCityAdapter).findAll(expectedPageable);

        assertThat(results, notNullValue());
        assertThat(results.getResults(), equalTo(persistenceMapper.toCitiesDto(cities.getContent())));
        assertThat(results.getTotalPages(), equalTo(2));
        assertThat(results.getSize(), equalTo(1));
    }

    @Test
    public void shouldGetCity() {

        City city = someCity();
        Optional<City> optionalCity = Optional.of(city);

        when(mockCityAdapter.findById(any())).thenReturn(optionalCity);

        CityDto results = service.getCity(ID);

        verify(mockCityAdapter).findById(ID);

        assertThat(results, notNullValue());
        assertThat(results, equalTo(persistenceMapper.toCityDto(city)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetANullCity() {

        Optional<City> optionalCity = Optional.empty();

        when(mockCityAdapter.findById(any())).thenReturn(optionalCity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, "city"));

        service.getCity(ID);
    }

    @Test
    public void shouldCreateACity() {

        City city = someCity();
        CityRequest cityRequest = someCityRequest();

        when(mockCityAdapter.save(any())).thenReturn(city);

        CityDto results = service.createCity(cityRequest);

        City expectedCity = persistenceMapper.toCity(cityRequest);

        verify(mockCityAdapter).save(expectedCity);

        assertThat(results, notNullValue());
        assertThat(results.getPostalCode(), equalTo(city.getPostalCode()));
        assertThat(results.getName(), equalTo(city.getName()));
    }

    @Test
    public void shouldCreateACityWithDepartment() {

        City city = someCity();
        Department department = someDepartment();
        CityRequest cityRequest = someCityRequest();

        when(mockCityAdapter.save(any())).thenReturn(city);
        when(mockDepartmentService.getDepartmentDao(any())).thenReturn(department);

        CityDto results = service.createCity(cityRequest);

        verify(mockDepartmentService).getDepartmentDao(cityRequest.getDepartmentId());

        assertThat(results, notNullValue());
        assertThat(results.getDepartment(), notNullValue());
        assertThat(results.getDepartment(), equalTo(persistenceMapper.toDepartmentDto(city.getDepartment())));
    }
}
