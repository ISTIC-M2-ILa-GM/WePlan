package fr.istic.gm.weplan.infra.repository;

import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.infra.TestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static fr.istic.gm.weplan.infra.TestData.someCity;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfiguration.class})
public class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    private City entity1;
    private City entity2;

    @Before
    public void setUp() {

        cityRepository.deleteAll();

        entity1 = someCity();
        entity1.setDepartment(null);
        entity2 = someCity();
        entity2.setDepartment(null);

        entity1 = cityRepository.save(entity1);
        entity2 = cityRepository.save(entity2);
    }

    @Test
    public void shouldFindAllPage() {

        Page<City> cities = cityRepository.findAll(PageRequest.of(0, 10));

        assertThat(cities, notNullValue());
        assertThat(cities.getTotalPages(), equalTo(1));
        assertThat(cities.getContent(), hasSize(2));
        assertThat(cities.getSize(), equalTo(10));
    }

    @Test
    public void shouldFindAllByDeletedAtIsNullPage() {

        entity1.setDeletedAt(Instant.now());
        entity1 = cityRepository.save(entity1);

        Page<City> cities = cityRepository.findAllByDeletedAtIsNull(PageRequest.of(0, 10));

        assertThat(cities, notNullValue());
        assertThat(cities.getTotalPages(), equalTo(1));
        assertThat(cities.getContent(), hasSize(1));
        assertThat(cities.getSize(), equalTo(10));
    }

    @Test
    public void shouldFindAllByDeletedAtIsNull() {

        entity1.setDeletedAt(Instant.now());
        entity1 = cityRepository.save(entity1);

        List<City> cities = cityRepository.findAllByDeletedAtIsNull();

        assertThat(cities, notNullValue());
        assertThat(cities, hasSize(1));
    }

    @Test
    public void shouldFindAllWithPage() {

        Page<City> cities = cityRepository.findAll(PageRequest.of(0, 1));

        assertThat(cities, notNullValue());
        assertThat(cities.getTotalPages(), equalTo(2));
        assertThat(cities.getContent(), hasSize(1));
        assertThat(cities.getSize(), equalTo(1));
    }

    @Test
    public void shouldGetOneCity() {

        Optional<City> city = cityRepository.findById(entity1.getId());

        assertThat(city, notNullValue());
        assertThat(city.isPresent(), equalTo(true));
        assertThat(city.get().getName(), equalTo(entity1.getName()));
        assertThat(city.get().getDepartment(), equalTo(entity1.getDepartment()));
        assertThat(city.get().getPostalCode(), equalTo(entity1.getPostalCode()));
        assertThat(city.get().getId(), equalTo(entity1.getId()));
    }

    @Test
    public void shouldGetOneCityByName() {

        Optional<City> city = cityRepository.findByName(entity1.getName());

        assertThat(city, notNullValue());
        assertThat(city.isPresent(), equalTo(true));
        assertThat(city.get().getName(), equalTo(entity1.getName()));
        assertThat(city.get().getDepartment(), equalTo(entity1.getDepartment()));
        assertThat(city.get().getPostalCode(), equalTo(entity1.getPostalCode()));
        assertThat(city.get().getId(), equalTo(entity1.getId()));
    }

    @Test
    public void shouldCreateACity() {

        City city = someCity();
        city.setId(null);
        city.setCreatedAt(null);
        city.setUpdatedAt(null);
        city.setDepartment(null);

        city = cityRepository.save(city);

        assertThat(city, notNullValue());
        assertThat(city.getId(), notNullValue());
        assertThat(city.getCreatedAt(), notNullValue());
        assertThat(city.getUpdatedAt(), notNullValue());
    }
}
