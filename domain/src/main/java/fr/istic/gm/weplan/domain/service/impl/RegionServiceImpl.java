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
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.log.LogMessage.*;

@AllArgsConstructor
@Slf4j
@Service
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
        if (result.isPresent()) {
            return this.persistenceMapper.toRegionDto(result.get());
        }else{
            throw new RuntimeException();
        }
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

    @Override
    public RegionDto updateRegion(Long id, HashMap<String, Object> map) {
        RegionDto region = this.getRegion(id);

        map.keySet().forEach(key -> {
            if (map.get(key) != null){
                switch (key) {
                    case "name":
                        region.setName(map.get(key).toString());
                        break;
                    default:
                        // do nothing
                        break;
                }
            }
        });

        Region input = this.persistenceMapper.toRegion(region);
        Region result = this.regionAdapter.update(input);

        RegionDto output = this.persistenceMapper.toRegionDto(result);

        return output;
    }
}