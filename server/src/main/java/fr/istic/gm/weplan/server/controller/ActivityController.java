package fr.istic.gm.weplan.server.controller;


import fr.istic.gm.weplan.domain.model.dto.ActivityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.request.ActivityRequest;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;
import fr.istic.gm.weplan.domain.service.ActivityService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.ACTIVITY;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.ID;

/**
 * The Activity API
 */
@Api(tags = "Activity Controller", description = "Activity API")
@AllArgsConstructor
@RestController
@RequestMapping(path = ACTIVITY, produces = "application/json")
public class ActivityController {
    private ActivityService activityService;

    @ApiOperation("Get activities")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get activities")
    })
    @GetMapping
    public PageDto<ActivityDto> getActivities(@ApiParam(value = "Page request", required = true) @RequestBody PageOptions pageOptions) {
        return this.activityService.getActivities(pageOptions);
    }

    @ApiOperation("Get activity")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get an activity with a given id"),
            @ApiResponse(code = 404, message = "Activity not found")
    })
    @GetMapping(path = ID)
    public ActivityDto getActivity(@ApiParam(value = "Activity id", required = true) @PathVariable Long id) {
        return this.activityService.getActivity(id);
    }

    @ApiOperation("Create an activity")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Create an activity")
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public ActivityDto postActivity(@ApiParam(value = "Activity request", required = true) @RequestBody ActivityRequest activity) {
        return this.activityService.createActivity(activity);
    }

    @ApiOperation("Patch an activity")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patch an activity with a given id"),
            @ApiResponse(code = 404, message = "Activity not found")
    })
    @PatchMapping(path = ID)
    public ActivityDto patchActivity(@ApiParam(value = "Activity id", required = true) @PathVariable Long id, @ApiParam(value = "Patch request", required = true) @RequestBody Map<String, Object> map) {
        return this.activityService.patchActivity(id, map);
    }

    @ApiOperation("Delete an activity")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Delete an activity with a given id"),
            @ApiResponse(code = 404, message = "Activity not found")
    })
    @DeleteMapping(path = ID)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteActivity(@ApiParam(value = "Activity id", required = true) @PathVariable Long id) {
        this.activityService.deleteActivity(id);
    }
}
