package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.CityAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.service.DepartmentDaoService;
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
import java.util.*;

import static fr.istic.gm.weplan.domain.TestData.*;
import static fr.istic.gm.weplan.domain.exception.DomainException.*;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private CityServiceImpl service;
    @Mock
    private CityAdapter mockCityAdapter;
    @Mock
    private DepartmentDaoService mockDepartmentService;
    @Mock
    private Clock mockClock;
    private PersistenceMapper persistenceMapper;

    @Before
    public void setUp() {
        persistenceMapper = Mappers.getMapper(PersistenceMapper.class);
        service = new CityServiceImpl(mockCityAdapter, mockDepartmentService, persistenceMapper, mockClock);
    }

    @Test
    public void shouldGetCityDao() {

        City city = someCity();
        Optional<City> optionalCity = Optional.of(city);

        when(mockCityAdapter.findById(any())).thenReturn(optionalCity);

        City result = service.getCityDao(ID);

        verify(mockCityAdapter).findById(ID);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(city));
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetANullDepartmentDao() {

        Optional<City> optionalCity = Optional.empty();

        when(mockCityAdapter.findById(any())).thenReturn(optionalCity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, City.class.getSimpleName()));

        service.getCityDao(ID);
    }

    @Test
    public void shouldGetCitiesPage() {

        PageRequest pageRequest = somePageOptions();
        Page<City> cities = new PageImpl<>(Collections.singletonList(someCity()), org.springframework.data.domain.PageRequest.of(1, 1), 2);

        when(mockCityAdapter.findAllByDeletedAtIsNull(any())).thenReturn(cities);

        PageDto<CityDto> results = service.getCities(pageRequest);

        org.springframework.data.domain.PageRequest expectedPageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getSize());

        verify(mockCityAdapter).findAllByDeletedAtIsNull(expectedPageable);

        assertThat(results, notNullValue());
        assertThat(results.getResults(), equalTo(persistenceMapper.toCitiesDto(cities.getContent())));
        assertThat(results.getTotalPages(), equalTo(2));
        assertThat(results.getSize(), equalTo(1));
    }

    @Test
    public void shouldGetCitiesPageWithoutPage() {

        List<City> cities = Collections.singletonList(someCity());

        when(mockCityAdapter.findAllByDeletedAtIsNull()).thenReturn(cities);

        PageDto<CityDto> results = service.getCities(null);


        verify(mockCityAdapter).findAllByDeletedAtIsNull();

        assertThat(results, notNullValue());
        assertThat(results.getResults(), equalTo(persistenceMapper.toCitiesDto(cities)));
        assertThat(results.getTotalPages(), equalTo(1));
        assertThat(results.getSize(), equalTo(1));
    }

    @Test
    public void shouldGetCities() {

        List<City> cities = Collections.singletonList(someCity());

        when(mockCityAdapter.findAllByDeletedAtIsNull()).thenReturn(cities);

        List<CityDto> results = service.getCities();

        verify(mockCityAdapter).findAllByDeletedAtIsNull();

        assertThat(results, notNullValue());
        assertThat(results, equalTo(persistenceMapper.toCitiesDto(cities)));
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
        thrown.expectMessage(String.format(NOT_FOUND_MSG, City.class.getSimpleName()));

        service.getCity(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetADeletedCity() {

        City city = someCity();
        city.setDeletedAt(Instant.now());
        Optional<City> optionalCity = Optional.of(city);

        when(mockCityAdapter.findById(any())).thenReturn(optionalCity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, City.class.getSimpleName()));

        service.getCity(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetACityWithNullId() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, City.class.getSimpleName()));

        service.getCity((Long) null);
    }

    @Test
    public void shouldGetCityByName() {

        City city = someCity();
        Optional<City> optionalCity = Optional.of(city);

        when(mockCityAdapter.findByName(any())).thenReturn(optionalCity);

        CityDto results = service.getCity(NAME);

        verify(mockCityAdapter).findByName(NAME);

        assertThat(results, notNullValue());
        assertThat(results, equalTo(persistenceMapper.toCityDto(city)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetANullCityByName() {

        Optional<City> optionalCity = Optional.empty();

        when(mockCityAdapter.findByName(any())).thenReturn(optionalCity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, City.class.getSimpleName()));

        service.getCity(NAME);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetADeletedCityByName() {

        City city = someCity();
        city.setDeletedAt(Instant.now());
        Optional<City> optionalCity = Optional.of(city);

        when(mockCityAdapter.findByName(any())).thenReturn(optionalCity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, City.class.getSimpleName()));

        service.getCity(NAME);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetACityWithNullName() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, City.class.getSimpleName()));

        service.getCity((String) null);
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

    @Test
    public void shouldDeleteACity() {

        Instant now = Instant.now();
        City city = someCity();
        city.setDepartment(null);
        Optional<City> optionalCity = Optional.of(city);

        when(mockCityAdapter.findById(any())).thenReturn(optionalCity);
        when(mockCityAdapter.save(any())).thenReturn(city);
        when(mockClock.instant()).thenReturn(now);

        service.deleteCity(ID);

        verify(mockCityAdapter).findById(ID);
        verify(mockCityAdapter).save(city);
        verify(mockClock).instant();

        assertThat(city.getDeletedAt(), notNullValue());
        assertThat(city.getDeletedAt(), equalTo(now));
    }

    @Test
    public void shouldThrowDomainExceptionWhenDeleteANullCity() {

        Optional<City> optionalCity = Optional.empty();

        when(mockCityAdapter.findById(any())).thenReturn(optionalCity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, City.class.getSimpleName()));

        service.deleteCity(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenDeleteADeletedCity() {

        City city = someCity();
        city.setDeletedAt(Instant.now());
        Optional<City> optionalCity = Optional.of(city);

        when(mockCityAdapter.findById(any())).thenReturn(optionalCity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, City.class.getSimpleName()));

        service.deleteCity(ID);
    }

    @Test
    public void shouldPatchACity() {

        City city = someCity();
        city.setDepartment(null);
        city.setId(ID);
        Optional<City> optionalCity = Optional.of(city);

        Map<String, Object> patch = new HashMap<>();
        patch.put("name", "a-new-name");
        patch.put("postalCode", 10000);
        patch.put("id", 18);

        when(mockCityAdapter.findById(any())).thenReturn(optionalCity);
        when(mockCityAdapter.save(any())).thenReturn(city);

        CityDto result = service.patchCity(ID, patch);

        verify(mockCityAdapter).findById(ID);
        verify(mockCityAdapter).save(city);

        assertThat(result, notNullValue());
        assertThat(result.getId(), equalTo(ID));
        assertThat(result.getName(), equalTo("a-new-name"));
        assertThat(result.getPostalCode(), equalTo(10000));
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchADeletedCity() {

        City city = someCity();
        city.setDeletedAt(Instant.now());
        Optional<City> optionalCity = Optional.of(city);

        when(mockCityAdapter.findById(any())).thenReturn(optionalCity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, City.class.getSimpleName()));

        service.patchCity(ID, new HashMap<>());
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchANullCity() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, City.class.getSimpleName()));

        service.patchCity(null, new HashMap<>());
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchACityWithANullPatch() {

        City city = someCity();
        city.setDeletedAt(null);
        city.setDepartment(null);
        Optional<City> optionalCity = Optional.of(city);

        when(mockCityAdapter.findById(any())).thenReturn(optionalCity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(NOTHING_TO_PATCH);

        service.patchCity(ID, null);
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchACityWithAnEmptyPatch() {

        City city = someCity();
        city.setDeletedAt(null);
        city.setDepartment(null);
        Optional<City> optionalCity = Optional.of(city);

        when(mockCityAdapter.findById(any())).thenReturn(optionalCity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(NOTHING_TO_PATCH);

        service.patchCity(ID, new HashMap<>());
    }

    @Test
    public void shouldThrowExceptionWhenPatchACityWithWrongType() {

        City city = someCity();
        city.setDeletedAt(null);
        city.setDepartment(null);
        Optional<City> optionalCity = Optional.of(city);

        Map<String, Object> patch = new HashMap<>();
        patch.put("name", new Object());

        when(mockCityAdapter.findById(any())).thenReturn(optionalCity);

        thrown.expect(DomainException.class);
        thrown.expectMessage(WRONG_DATA_TO_PATCH);

        service.patchCity(ID, patch);
    }

    @Test
    public void shouldGetCitiesDao() {
        when(mockCityAdapter.findAllByDeletedAtIsNull()).thenReturn(singletonList(someCity()));

        List<City> citiesDao = this.service.getCitiesDao();
        verify(mockCityAdapter).findAllByDeletedAtIsNull();

        assertThat(citiesDao, notNullValue());
        assertThat(citiesDao, hasSize(1));
    }
}
