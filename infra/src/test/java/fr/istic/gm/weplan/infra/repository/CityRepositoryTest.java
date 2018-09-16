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

    @Before
    public void setUp() {
        cityRepository.deleteAll();
    }

    @Test
    public void shouldFindAll() {

        City entity1 = someCity();
        entity1.setDepartment(null);
        City entity2 = someCity();
        entity2.setDepartment(null);

        cityRepository.save(entity1);
        cityRepository.save(entity2);

        Page<City> cities = cityRepository.findAll(PageRequest.of(0, 10));

        assertThat(cities, notNullValue());
        assertThat(cities.getTotalPages(), equalTo(1));
        assertThat(cities.getContent(), hasSize(2));
        assertThat(cities.getSize(), equalTo(10));
    }

    @Test
    public void shouldFindAllWithPage() {

        City entity1 = someCity();
        entity1.setDepartment(null);
        City entity2 = someCity();
        entity2.setDepartment(null);

        cityRepository.save(entity1);
        cityRepository.save(entity2);

        Page<City> cities = cityRepository.findAll(PageRequest.of(0, 1));

        assertThat(cities, notNullValue());
        assertThat(cities.getTotalPages(), equalTo(2));
        assertThat(cities.getContent(), hasSize(1));
        assertThat(cities.getSize(), equalTo(1));
    }
}
