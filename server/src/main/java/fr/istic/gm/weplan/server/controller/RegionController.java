package fr.istic.gm.weplan.server.controller;


import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.service.RegionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static fr.istic.gm.weplan.server.log.LogMessage.*;
import static fr.istic.gm.weplan.server.config.ApiRoutes.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = REGION, produces = "application/json")
public class RegionController {
    private RegionService regionService;

    @GetMapping
    public PageDto<RegionDto> getRegions(@RequestBody PageOptions pageOptions) {
        log.info(API_MESSAGE, "", GET_REGIONS, pageOptions);
        PageDto<RegionDto> regions = this.regionService.getRegions(pageOptions);
        log.info(API_MESSAGE, "", REGIONS_GOTTEN, "");

        return regions;
    }

    @GetMapping(path = ID)
    public RegionDto getRegion(@PathVariable Long id) {
        return this.regionService.getRegion(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public RegionDto postRegion(@RequestBody RegionDto region) {
        return this.regionService.createRegion(region);
    }

    @PatchMapping(path = ID)
    public RegionDto patchRegion(@PathVariable Long id, @RequestBody HashMap<String, Object> map) {
        return this.regionService.updateRegion(id, map);
    }

    @DeleteMapping(path = ID)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteRegion(@PathVariable Long id) {
        this.regionService.deleteRegion(id);
    }
}
