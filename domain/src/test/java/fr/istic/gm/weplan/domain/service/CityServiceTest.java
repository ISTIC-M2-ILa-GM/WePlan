package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.adapter.CityAdapter;
import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.service.impl.CityServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static fr.istic.gm.weplan.domain.TestData.someCity;
import static fr.istic.gm.weplan.domain.TestData.somePageOptions;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    private CityService service;

    @Mock
    private CityAdapter mockCityAdapter;

    private PersistenceMapper persistenceMapper;

    @Before
    public void setUp() {
        persistenceMapper = Mappers.getMapper(PersistenceMapper.class);
        service = new CityServiceImpl(mockCityAdapter, persistenceMapper);
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
}
