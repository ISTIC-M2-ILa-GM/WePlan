package fr.istic.gm.weplan.server.controller;


import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.service.RegionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

import static fr.istic.gm.weplan.server.log.LogMessage.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/region", produces = "application/json")
public class RegionController {
    private RegionService regionService;

    @GetMapping
    public PageDto<RegionDto> getRegions(PageOptions pageOptions) {
        log.info(API_MESSAGE, "", GET_REGIONS, pageOptions);
        PageDto<RegionDto> regions = this.regionService.getRegions(pageOptions);
        log.info(API_MESSAGE, "", REGIONS_GOTTEN, "");

        return regions;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public RegionDto createRegion(RegionDto region) {
        return this.regionService.createRegion(region);
    }

    @GetMapping(path = "/{id}")
    public RegionDto getRegion(@PathVariable Long id) {
        return this.regionService.getRegion(id);
    }

    @PatchMapping(path = "/{id}")
    public RegionDto getRegion(@PathVariable Long id, HashMap<String, Object> map) {
        return this.regionService.updateRegion(id, map);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRegion(@PathVariable Long id) {
        this.regionService.deleteRegion(id);
    }
}
