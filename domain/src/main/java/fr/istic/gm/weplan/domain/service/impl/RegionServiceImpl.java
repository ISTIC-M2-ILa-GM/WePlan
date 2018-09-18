package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.RegionAdapter;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.service.RegionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static fr.istic.gm.weplan.domain.log.LogMessage.*;

@AllArgsConstructor
@Slf4j
public class RegionServiceImpl implements RegionService {
    private RegionAdapter regionAdapter;

    private PersistenceMapper persistenceMapper;

    @Override
    public PageDto<RegionDto> getRegions(PageOptions pageOptions) {
        log.info(SERVICE_MESSAGE, "", GET_CITIES, pageOptions);

        Page<Region> regions = regionAdapter.findAll(PageRequest.of(pageOptions.getPage(), pageOptions.getSize()));
        PageDto<RegionDto> regionDto = PageDto.<RegionDto>builder()
                .results(persistenceMapper.toRegionsDto(regions.getContent()))
                .totalPages(regions.getTotalPages())
                .size(regions.getSize())
                .build();

        log.info(SERVICE_MESSAGE, "", CITIES_GOTTEN, "");

        return regionDto;
    }

    @Override
    public RegionDto getRegion(Long id) {
        Optional<Region> result = this.regionAdapter.findById(id);

        return this.persistenceMapper.toRegionDto(result.get());
    }

    @Override
    public RegionDto createRegion(RegionDto regionDto) {
        log.info(SERVICE_MESSAGE, "", POST_REGION, regionDto);

        Region input = this.persistenceMapper.toRegion(regionDto);
        Region result = this.regionAdapter.save(input);

        RegionDto output = this.persistenceMapper.toRegionDto(result);

        log.info(SERVICE_MESSAGE, "", POST_REGION, "");

        return output;
    }
}