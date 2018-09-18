package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;

public interface RegionService {
    PageDto<RegionDto> getRegions(PageOptions pageOptions);

    RegionDto getRegion(Long id);

    RegionDto createRegion(RegionDto region);
}
