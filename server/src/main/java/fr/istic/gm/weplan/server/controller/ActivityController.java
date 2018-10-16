package fr.istic.gm.weplan.server.controller;


import fr.istic.gm.weplan.domain.model.dto.ActivityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.request.ActivityRequest;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.service.ActivityService;
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
    public PageDto<ActivityDto> getActivities(@ApiParam(value = "Page request") @RequestBody(required = false) PageRequest pageRequest) {
        return this.activityService.getActivities(pageRequest);
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
    public ActivityDto postActivity(@Valid @ApiParam(value = "Activity request", required = true) @RequestBody ActivityRequest activity) {
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
