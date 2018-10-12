package fr.istic.gm.weplan.server.controller;


import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;
import fr.istic.gm.weplan.domain.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

import javax.validation.Valid;
import java.util.Map;

import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.ID;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.REGION;

/**
 * The Region API
 */
@Api(tags = "Region Controller", description = "Region API")
@AllArgsConstructor
@RestController
@RequestMapping(path = REGION, produces = "application/json")
public class RegionController {
    private RegionService regionService;

    @ApiOperation("Get regions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get regions")
    })
    @GetMapping
    public PageDto<RegionDto> getRegions(@ApiParam(value = "Page request", required = true) @RequestBody PageOptions pageOptions) {
        return this.regionService.getRegions(pageOptions);
    }

    @ApiOperation("Get region")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get a region with a given id"),
            @ApiResponse(code = 404, message = "Region not found")
    })
    @GetMapping(path = ID)
    public RegionDto getRegion(@ApiParam(value = "Region id", required = true) @PathVariable Long id) {
        return this.regionService.getRegion(id);
    }

    @ApiOperation("Create a region")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Create a region")
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public RegionDto postRegion(@Valid @ApiParam(value = "Region request", required = true) @RequestBody RegionRequest region) {
        return this.regionService.createRegion(region);
    }

    @ApiOperation("Patch a region")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patch a region with a given id"),
            @ApiResponse(code = 404, message = "Region not found")
    })
    @PatchMapping(path = ID)
    public RegionDto patchRegion(@ApiParam(value = "Region id", required = true) @PathVariable Long id, @ApiParam(value = "Patch request", required = true) @RequestBody Map<String, Object> map) {
        return this.regionService.patchRegion(id, map);
    }

    @ApiOperation("Delete a region")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Delete a region with a given id"),
            @ApiResponse(code = 404, message = "Region not found")
    })
    @DeleteMapping(path = ID)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteRegion(@ApiParam(value = "Region id", required = true) @PathVariable Long id) {
        this.regionService.deleteRegion(id);
    }
}
