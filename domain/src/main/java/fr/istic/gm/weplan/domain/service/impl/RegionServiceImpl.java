package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.RegionAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;
import fr.istic.gm.weplan.domain.service.PatchService;
import fr.istic.gm.weplan.domain.service.RegionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.NOT_FOUND;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static fr.istic.gm.weplan.domain.log.LogMessage.*;

@AllArgsConstructor
@Slf4j
@Service
public class RegionServiceImpl extends PatchService<Region> implements RegionService {
    private RegionAdapter regionAdapter;

    private PersistenceMapper persistenceMapper;

    private Clock clock;

    @Override
    public PageDto<RegionDto> getRegions(PageOptions pageOptions) {
        log.info(SERVICE_MESSAGE, "", GET_CITIES, pageOptions);

        Page<Region> regions = regionAdapter.findAll(PageRequest.of(pageOptions.getPage(), pageOptions.getSize()));

        PageDto<RegionDto> regionsDto = persistenceMapper.toRegionsPageDto(regions);

        log.info(SERVICE_MESSAGE, "", CITIES_GOTTEN, "");

        return regionsDto;
    }

    @Override
    public RegionDto getRegion(Long id) {
        Optional<Region> result = this.regionAdapter.findById(id);
        if (result.isPresent()) {
            return this.persistenceMapper.toRegionDto(result.get());
        }else{
            throw new RuntimeException();
        }
    }

    @Override
    public RegionDto createRegion(RegionRequest regionRequest) {
        log.info(SERVICE_MESSAGE, "", POST_REGION, regionRequest);

        Region input = this.persistenceMapper.toRegion(regionRequest);
        Region result = this.regionAdapter.save(input);

        RegionDto output = this.persistenceMapper.toRegionDto(result);

        log.info(SERVICE_MESSAGE, "", POST_REGION, "");

        return output;
    }

    @Override
    public RegionDto updateRegion(Long id, Map<String, Object> map) {
        Region region = this.getAndVerifyRegion(id);

        this.patch(region, map);

        Region result = this.regionAdapter.save(region);

        return this.persistenceMapper.toRegionDto(result);
    }

    @Override
    public void deleteRegion(Long id) {
        Region region = this.getAndVerifyRegion(id);
        region.setDeletedAt(this.clock.instant());

        this.regionAdapter.save(region);
    }

    private Region getAndVerifyRegion(Long id) {
        Optional<Region> region = regionAdapter.findById(id);
        if (!region.isPresent()) {
            DomainException e = new DomainException(NOT_FOUND_MSG, "region", NOT_FOUND);
            log.error(e.getMessage(), e);
            throw e;
        }
        return region.get();
    }
}