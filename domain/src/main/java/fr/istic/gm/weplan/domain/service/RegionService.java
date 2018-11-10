package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;

import java.util.List;
import java.util.Map;

public interface RegionService {
    PageDto<RegionDto> getRegions(PageRequest pageRequest);

    List<RegionDto> getRegions();

    RegionDto getRegion(Long id);

    RegionDto getRegion(String name);

    RegionDto createRegion(RegionRequest region);

    RegionDto patchRegion(Long id, Map<String, Object> map);

    void deleteRegion(Long id);
}
