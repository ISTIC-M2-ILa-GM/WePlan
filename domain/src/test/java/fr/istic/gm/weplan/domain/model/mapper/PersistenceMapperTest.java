package fr.istic.gm.weplan.domain.model.mapper;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.entities.City;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fr.istic.gm.weplan.domain.TestData.someCity;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
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

        assertThat(mapper.toCityDto(null), equalTo(null));

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

        assertThat(mapper.toCitiesDto(null), equalTo(null));
        assertThat(mapper.toCitiesDto(new ArrayList<>()), hasSize(0));
        assertThat(mapper.toCitiesDto(Collections.singletonList(someCity())), hasSize(1));

        List<City> cities = new ArrayList<>();
        cities.add(someCity());
        cities.add(someCity());
        assertThat(mapper.toCitiesDto(cities), hasSize(2));
    }
}
