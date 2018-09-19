package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;

import java.util.HashMap;
import java.util.Map;

public interface RegionService {
    PageDto<RegionDto> getRegions(PageOptions pageOptions);

    RegionDto getRegion(Long id);

    RegionDto createRegion(RegionRequest region);

    RegionDto updateRegion(Long id, Map<String, Object> map);

    void deleteRegion(Long id);
}
