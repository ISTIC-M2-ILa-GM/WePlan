package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;

import java.util.HashMap;

public interface RegionService {
    PageDto<RegionDto> getRegions(PageOptions pageOptions);

    RegionDto getRegion(Long id);

    RegionDto createRegion(RegionDto region);

    RegionDto updateRegion(Long id, HashMap<String, Object> map);

    void deleteRegion(Long id);
}
