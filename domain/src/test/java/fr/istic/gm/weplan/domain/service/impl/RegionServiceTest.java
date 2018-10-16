package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.RegionAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.TestData.ID;
import static fr.istic.gm.weplan.domain.TestData.somePageOptions;
import static fr.istic.gm.weplan.domain.TestData.someRegion;
import static fr.istic.gm.weplan.domain.TestData.someRegionRequest;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOTHING_TO_PATCH;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static fr.istic.gm.weplan.domain.exception.DomainException.WRONG_DATA_TO_PATCH;
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

    @Test
    public void shouldGetRegionsPage() {

        PageRequest pageRequest = somePageOptions();
        Page<Region> regions = new PageImpl<>(Collections.singletonList(someRegion()), org.springframework.data.domain.PageRequest.of(1, 1), 2);

        when(mockRegionAdapter.findAllByDeletedAtIsNull(any())).thenReturn(regions);

        PageDto<RegionDto> results = service.getRegions(pageRequest);

        org.springframework.data.domain.PageRequest expectedPageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getSize());

        verify(mockRegionAdapter).findAllByDeletedAtIsNull(expectedPageable);

        assertThat(results, notNullValue());
        assertThat(results.getResults(), equalTo(persistenceMapper.toRegionsDto(regions.getContent())));
        assertThat(results.getTotalPages(), equalTo(2));
        assertThat(results.getSize(), equalTo(1));
    }

    @Test
    public void shouldGetRegionsPageWithout() {

        List<Region> regions = Collections.singletonList(someRegion());

        when(mockRegionAdapter.findAllByDeletedAtIsNull()).thenReturn(regions);

        PageDto<RegionDto> results = service.getRegions(null);

        verify(mockRegionAdapter).findAllByDeletedAtIsNull();

        assertThat(results, notNullValue());
        assertThat(results.getResults(), equalTo(persistenceMapper.toRegionsDto(regions)));
        assertThat(results.getTotalPages(), equalTo(1));
        assertThat(results.getSize(), equalTo(1));
    }

    @Test
    public void shouldGetRegions() {

        List<Region> regions = Collections.singletonList(someRegion());

        when(mockRegionAdapter.findAllByDeletedAtIsNull()).thenReturn(regions);

        List<RegionDto> results = service.getRegions();

        verify(mockRegionAdapter).findAllByDeletedAtIsNull();

        assertThat(results, notNullValue());
        assertThat(results, equalTo(persistenceMapper.toRegionsDto(regions)));
    }

    @Test
    public void shouldGetRegion() {

        Region region = someRegion();
        Optional<Region> optionalRegion = Optional.of(region);

        when(mockRegionAdapter.findById(any())).thenReturn(optionalRegion);

        RegionDto results = service.getRegion(ID);

        verify(mockRegionAdapter).findById(ID);

        assertThat(results, notNullValue());
        assertThat(results, equalTo(persistenceMapper.toRegionDto(region)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetANullRegion() {

        Optional<Region> optionalRegion = Optional.empty();

        when(mockRegionAdapter.findById(any())).thenReturn(optionalRegion);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Region.class.getSimpleName()));

        service.getRegion(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetADeletedRegion() {

        Region region = someRegion();
        region.setDeletedAt(Instant.now());
        Optional<Region> optionalRegion = Optional.of(region);

        when(mockRegionAdapter.findById(any())).thenReturn(optionalRegion);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Region.class.getSimpleName()));

        service.getRegion(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetARegionWithNullId() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Region.class.getSimpleName()));

        service.getRegion(null);
    }

    @Test
    public void shouldCreateARegion() {

        Region region = someRegion();
        RegionRequest regionRequest = someRegionRequest();

        when(mockRegionAdapter.save(any())).thenReturn(region);

        RegionDto results = service.createRegion(regionRequest);

        Region expectedRegion = persistenceMapper.toRegion(regionRequest);

        verify(mockRegionAdapter).save(expectedRegion);

        assertThat(results, notNullValue());
        assertThat(results.getName(), equalTo(region.getName()));
    }

    @Test
    public void shouldDeleteARegion() {

        Instant now = Instant.now();
        Region region = someRegion();
        Optional<Region> optionalRegion = Optional.of(region);

        when(mockRegionAdapter.findById(any())).thenReturn(optionalRegion);
        when(mockRegionAdapter.save(any())).thenReturn(region);
        when(mockClock.instant()).thenReturn(now);

        service.deleteRegion(ID);

        verify(mockRegionAdapter).findById(ID);
        verify(mockRegionAdapter).save(region);
        verify(mockClock).instant();

        assertThat(region.getDeletedAt(), notNullValue());
        assertThat(region.getDeletedAt(), equalTo(now));
    }

    @Test
    public void shouldThrowDomainExceptionWhenDeleteANullRegion() {

        Optional<Region> optionalRegion = Optional.empty();

        when(mockRegionAdapter.findById(any())).thenReturn(optionalRegion);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Region.class.getSimpleName()));

        service.deleteRegion(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenDeleteADeletedRegion() {

        Region region = someRegion();
        region.setDeletedAt(Instant.now());
        Optional<Region> optionalRegion = Optional.of(region);

        when(mockRegionAdapter.findById(any())).thenReturn(optionalRegion);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Region.class.getSimpleName()));

        service.deleteRegion(ID);
    }

    @Test
    public void shouldPatchARegion() {

        Region region = someRegion();
        region.setId(ID);
        Optional<Region> optionalRegion = Optional.of(region);

        Map<String, Object> patch = new HashMap<>();
        patch.put("name", "a-new-name");
        patch.put("id", 18);

        when(mockRegionAdapter.findById(any())).thenReturn(optionalRegion);
        when(mockRegionAdapter.save(any())).thenReturn(region);

        RegionDto result = service.patchRegion(ID, patch);

        verify(mockRegionAdapter).findById(ID);
        verify(mockRegionAdapter).save(region);

        assertThat(result, notNullValue());
        assertThat(result.getId(), equalTo(ID));
        assertThat(result.getName(), equalTo("a-new-name"));
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchADeletedRegion() {

        Region region = someRegion();
        region.setDeletedAt(Instant.now());
        Optional<Region> optionalRegion = Optional.of(region);

        when(mockRegionAdapter.findById(any())).thenReturn(optionalRegion);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Region.class.getSimpleName()));

        service.patchRegion(ID, new HashMap<>());
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchANullRegion() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, Region.class.getSimpleName()));

        service.patchRegion(null, new HashMap<>());
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchARegionWithANullPatch() {

        Region region = someRegion();
        region.setDeletedAt(null);
        Optional<Region> optionalRegion = Optional.of(region);

        when(mockRegionAdapter.findById(any())).thenReturn(optionalRegion);

        thrown.expect(DomainException.class);
        thrown.expectMessage(NOTHING_TO_PATCH);

        service.patchRegion(ID, null);
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchARegionWithAnEmptyPatch() {

        Region region = someRegion();
        region.setDeletedAt(null);
        Optional<Region> optionalRegion = Optional.of(region);

        when(mockRegionAdapter.findById(any())).thenReturn(optionalRegion);

        thrown.expect(DomainException.class);
        thrown.expectMessage(NOTHING_TO_PATCH);

        service.patchRegion(ID, new HashMap<>());
    }

    @Test
    public void shouldThrowExceptionWhenPatchARegionWithWrongType() {

        Region region = someRegion();
        region.setDeletedAt(null);
        Optional<Region> optionalRegion = Optional.of(region);

        Map<String, Object> patch = new HashMap<>();
        patch.put("name", new Object());

        when(mockRegionAdapter.findById(any())).thenReturn(optionalRegion);

        thrown.expect(DomainException.class);
        thrown.expectMessage(WRONG_DATA_TO_PATCH);

        service.patchRegion(ID, patch);
    }
}
