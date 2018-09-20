package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.RegionAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.TestData.ID;
import static fr.istic.gm.weplan.domain.TestData.someRegion;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegionServiceTest {

    private RegionServiceImpl service;

    @Mock
    private RegionAdapter mockRegionAdapter;

    @Mock
    private Clock mockClock;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private PersistenceMapper persistenceMapper;

    @Before
    public void setUp() {
        persistenceMapper = Mappers.getMapper(PersistenceMapper.class);
        service = new RegionServiceImpl(mockRegionAdapter, persistenceMapper, mockClock);
    }

    @Test
    public void shouldGetRegionDao() {

        Region region = someRegion();
        Optional<Region> optionalRegion = Optional.of(region);

        when(mockRegionAdapter.findById(any())).thenReturn(optionalRegion);

        Region result = service.getRegionDao(ID);

        verify(mockRegionAdapter).findById(ID);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(region));
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetANullRegionDao() {

        Optional<Region> optionalRegion = Optional.empty();

        when(mockRegionAdapter.findById(any())).thenReturn(optionalRegion);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Region.class.getSimpleName()));

        service.getRegionDao(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetADeletedRegionDao() {

        Region region = someRegion();
        region.setDeletedAt(Instant.now());
        Optional<Region> optionalRegion = Optional.of(region);

        when(mockRegionAdapter.findById(any())).thenReturn(optionalRegion);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Region.class.getSimpleName()));

        service.getRegionDao(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetADeletedDao() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Region.class.getSimpleName()));

        service.getRegionDao(null);
    }
}
