package fr.istic.gm.weplan.server.controller;


import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;
import fr.istic.gm.weplan.domain.service.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static fr.istic.gm.weplan.server.config.ApiRoutes.ID;
import static fr.istic.gm.weplan.server.config.ApiRoutes.REGION;

@AllArgsConstructor
@RestController
@RequestMapping(path = REGION, produces = "application/json")
public class RegionController {
    private RegionService regionService;

    @GetMapping
    public PageDto<RegionDto> getRegions(@RequestBody PageOptions pageOptions) {
        return this.regionService.getRegions(pageOptions);
    }

    @GetMapping(path = ID)
    public RegionDto getRegion(@PathVariable Long id) {
        return this.regionService.getRegion(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public RegionDto postRegion(@RequestBody RegionRequest region) {
        return this.regionService.createRegion(region);
    }

    @PatchMapping(path = ID)
    public RegionDto patchRegion(@PathVariable Long id, @RequestBody Map<String, Object> map) {
        return this.regionService.updateRegion(id, map);
    }

    @DeleteMapping(path = ID)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteRegion(@PathVariable Long id) {
        this.regionService.deleteRegion(id);
    }
}
