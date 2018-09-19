package fr.istic.gm.weplan.domain.model.mapper;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fr.istic.gm.weplan.domain.TestData.someCity;
import static fr.istic.gm.weplan.domain.TestData.someCityRequest;
import static fr.istic.gm.weplan.domain.TestData.someDepartment;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(MockitoJUnitRunner.class)
public class PersistenceMapperTest {

    private PersistenceMapper mapper;

    @Before
    public void setUp() {
        mapper = Mappers.getMapper(PersistenceMapper.class);
    }

    @Test
    public void shouldMapCity() {

        assertThat(mapper.toCityDto(null), nullValue());

        City city = someCity();
        CityDto cityDto = mapper.toCityDto(city);
        assertThat(cityDto, notNullValue());
        assertThat(cityDto.getName(), equalTo(city.getName()));
        assertThat(cityDto.getPostalCode(), equalTo(city.getPostalCode()));
        assertThat(cityDto.getCreatedAt(), equalTo(city.getCreatedAt()));
        assertThat(cityDto.getDeletedAt(), equalTo(city.getDeletedAt()));
        assertThat(cityDto.getId(), equalTo(city.getId()));
        assertThat(cityDto.getUpdatedAt(), equalTo(city.getUpdatedAt()));
        assertThat(cityDto.getDepartment(), notNullValue());
    }

    @Test
    public void shouldMapCities() {

        assertThat(mapper.toCitiesDto(null), nullValue());
        assertThat(mapper.toCitiesDto(new ArrayList<>()), hasSize(0));
        assertThat(mapper.toCitiesDto(Collections.singletonList(someCity())), hasSize(1));

        List<City> cities = new ArrayList<>();
        cities.add(someCity());
        cities.add(someCity());
        assertThat(mapper.toCitiesDto(cities), hasSize(2));
    }

    @Test
    public void shouldMapCityRequest() {

        assertThat(mapper.toCity(null), nullValue());

        CityRequest cityRequest = someCityRequest();
        City city = mapper.toCity(cityRequest);
        assertThat(city, notNullValue());
        assertThat(city.getName(), equalTo(cityRequest.getName()));
        assertThat(city.getPostalCode(), equalTo(cityRequest.getPostalCode()));
        assertThat(city.getDepartment(), nullValue());
    }

    @Test
    public void shouldMapADepartment() {

        assertThat(mapper.toDepartmentDto(null), nullValue());

        Department department = someDepartment();
        DepartmentDto departmentDto = mapper.toDepartmentDto(department);
        assertThat(departmentDto, notNullValue());
        assertThat(departmentDto.getName(), equalTo(department.getName()));
        assertThat(departmentDto.getCode(), equalTo(department.getCode()));
        assertThat(departmentDto.getRegion(), notNullValue());
    }

    @Test
    public void shouldMapAPageCity() {
        assertThat(mapper.toCitiesPageDto(null), nullValue());

        City city = someCity();
        PageImpl<City> page = new PageImpl<>(Collections.singletonList(city), PageRequest.of(1, 2), 10);

        PageDto<CityDto> citiesPageDto = mapper.toCitiesPageDto(page);

        assertThat(citiesPageDto, notNullValue());
        assertThat(citiesPageDto.getSize(), equalTo(2));
        assertThat(citiesPageDto.getTotalPages(), equalTo(5));
        assertThat(citiesPageDto.getResults(), hasSize(1));
        assertThat(citiesPageDto.getResults().get(0).getId(), equalTo(city.getId()));
    }
}
