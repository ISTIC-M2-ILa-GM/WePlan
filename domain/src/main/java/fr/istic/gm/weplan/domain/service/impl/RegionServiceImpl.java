package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.RegionAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;
import fr.istic.gm.weplan.domain.service.RegionDaoService;
import fr.istic.gm.weplan.domain.service.RegionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.NOT_FOUND;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;

@AllArgsConstructor
@Slf4j
@Service
public class RegionServiceImpl extends PatchService<Region> implements RegionService, RegionDaoService {
    private RegionAdapter regionAdapter;

    private PersistenceMapper persistenceMapper;

    private Clock clock;

    @Override
    public Region getRegionDao(Long id) {

        Optional<Region> region = id != null ? regionAdapter.findById(id) : Optional.empty();
        if (!region.isPresent() || region.get().getDeletedAt() != null) {
            throw new DomainException(NOT_FOUND_MSG, Region.class.getSimpleName(), NOT_FOUND);
        }
        return region.get();
    }

    @Override
    public PageDto<RegionDto> getRegions(PageOptions pageOptions) {
        Page<Region> regions = regionAdapter.findAllByDeletedAtIsNull(PageRequest.of(pageOptions.getPage(), pageOptions.getSize()));

        return persistenceMapper.toRegionsPageDto(regions);
    }

    @Override
    public List<RegionDto> getRegions() {

        List<Region> regions = regionAdapter.findAllByDeletedAtIsNull();
        return persistenceMapper.toRegionsDto(regions);
    }

    @Override
    public RegionDto getRegion(Long id) {
        Region region = this.getRegionDao(id);

        return this.persistenceMapper.toRegionDto(region);
    }

    @Override
    public RegionDto createRegion(RegionRequest regionRequest) {
        Region input = this.persistenceMapper.toRegion(regionRequest);

        Region result = this.regionAdapter.save(input);

        return this.persistenceMapper.toRegionDto(result);
    }

    @Override
    public RegionDto patchRegion(Long id, Map<String, Object> map) {
        Region region = this.getRegionDao(id);

        this.patch(region, map);

        Region result = this.regionAdapter.save(region);

        return this.persistenceMapper.toRegionDto(result);
    }

    @Override
    public void deleteRegion(Long id) {
        Region region = this.getRegionDao(id);
        region.setDeletedAt(this.clock.instant());

        this.regionAdapter.save(region);
    }
}