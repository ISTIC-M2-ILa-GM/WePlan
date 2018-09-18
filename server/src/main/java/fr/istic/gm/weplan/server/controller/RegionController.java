package fr.istic.gm.weplan.server.controller;


import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.service.RegionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static fr.istic.gm.weplan.server.log.LogMessage.*;

@AllArgsConstructor
@Slf4j
public class RegionController {
    private RegionService regionService;

    public PageDto<RegionDto> getRegions(PageOptions pageOptions) {
        log.info(API_MESSAGE, "", GET_REGIONS, pageOptions);
        PageDto<RegionDto> regions = this.regionService.getRegions(pageOptions);
        log.info(API_MESSAGE, "", REGIONS_GOTTEN, "");

        return regions;
    }

    public RegionDto getRegion(Long id) {
        return this.regionService.getRegion(id);
    }

    public RegionDto createRegion(RegionDto region) {
        return this.regionService.createRegion(region);
    }
}
