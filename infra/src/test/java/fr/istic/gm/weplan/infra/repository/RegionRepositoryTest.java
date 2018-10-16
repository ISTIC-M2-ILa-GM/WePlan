package fr.istic.gm.weplan.infra.repository;

import fr.istic.gm.weplan.domain.model.entities.Region;
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

import static fr.istic.gm.weplan.infra.TestData.someRegion;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfiguration.class})
public class RegionRepositoryTest {

    @Autowired
    private RegionRepository regionRepository;

    private Region entity1;

    @Before
    public void setUp() {

        regionRepository.deleteAll();

        entity1 = regionRepository.save(someRegion());
        regionRepository.save(someRegion());
    }

    @Test
    public void shouldFindAll() {

        Page<Region> regions = regionRepository.findAll(PageRequest.of(0, 10));

        assertThat(regions, notNullValue());
        assertThat(regions.getTotalPages(), equalTo(1));
        assertThat(regions.getContent(), hasSize(2));
        assertThat(regions.getSize(), equalTo(10));
    }

    @Test
    public void shouldFindAllByDeletedAtIsNullPage() {

        entity1.setDeletedAt(Instant.now());
        entity1 = regionRepository.save(entity1);

        Page<Region> regions = regionRepository.findAllByDeletedAtIsNull(PageRequest.of(0, 10));

        assertThat(regions, notNullValue());
        assertThat(regions.getTotalPages(), equalTo(1));
        assertThat(regions.getContent(), hasSize(1));
        assertThat(regions.getSize(), equalTo(10));
    }

    @Test
    public void shouldFindAllByDeletedAtIsNull() {

        entity1.setDeletedAt(Instant.now());
        entity1 = regionRepository.save(entity1);

        List<Region> regions = regionRepository.findAllByDeletedAtIsNull();

        assertThat(regions, notNullValue());
        assertThat(regions, hasSize(1));
    }

    @Test
    public void shouldFindAllWithPage() {

        Page<Region> regions = regionRepository.findAll(PageRequest.of(0, 1));

        assertThat(regions, notNullValue());
        assertThat(regions.getTotalPages(), equalTo(2));
        assertThat(regions.getContent(), hasSize(1));
        assertThat(regions.getSize(), equalTo(1));
    }

    @Test
    public void shouldGetOneRegion() {

        Optional<Region> region = regionRepository.findById(entity1.getId());

        assertThat(region, notNullValue());
        assertThat(region.isPresent(), equalTo(true));
        assertThat(region.get().getName(), equalTo(entity1.getName()));
        assertThat(region.get().getId(), equalTo(entity1.getId()));
    }

    @Test
    public void shouldCreateARegion() {

        Region region = someRegion();

        region = regionRepository.save(region);

        assertThat(region, notNullValue());
        assertThat(region.getId(), notNullValue());
        assertThat(region.getCreatedAt(), notNullValue());
        assertThat(region.getUpdatedAt(), notNullValue());
    }
}
