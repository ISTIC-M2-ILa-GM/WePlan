package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.model.request.UserRequest;
import fr.istic.gm.weplan.domain.service.UserService;
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

import java.util.List;
import java.util.Map;

import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.ACTIVITIES;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.CITIES;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.DEPARTMENTS;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.EVENTS;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.ID;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.REGIONS;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.USER;

/**
 * User Controller
 */
@Api(tags = "User Controller", description = "User API")
@AllArgsConstructor
@RestController
@RequestMapping(path = USER, produces = "application/json")
public class UserController {

    private UserService userService;

    /**
     * Retrieve all users.
     *
     * @param pageOptions the page options
     * @return the users pageable
     */
    @ApiOperation("Get users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get users")
    })
    @GetMapping
    public PageDto<UserDto> getUsers(@ApiParam(value = "Page request", required = true) @RequestBody PageOptions pageOptions) {
        return userService.getUsers(pageOptions);
    }


    /**
     * Retrieve a user.
     *
     * @param id the id to retrieve
     * @return the user
     */
    @ApiOperation("Get a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get a user with a given id"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @GetMapping(path = ID)
    public UserDto getUser(@ApiParam(value = "User id", required = true) @PathVariable Long id) {
        return userService.getUser(id);
    }

    /**
     * Create a user.
     *
     * @param userRequest the user to create.
     * @return the user created
     */
    @ApiOperation("Create a user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Create a user")
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDto createUser(@ApiParam(value = "User request", required = true) @RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    /**
     * Delete a user.
     *
     * @param id the id to delete
     */
    @ApiOperation("Delete a user")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Delete a user with a given id"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @DeleteMapping(path = ID)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@ApiParam(value = "User id", required = true) @PathVariable Long id) {
        userService.deleteUser(id);
    }

    /**
     * Patch a user with new data.
     *
     * @param id    the id of the user to patch
     * @param patch the map of the field to patch
     * @return the updated user
     */
    @ApiOperation("Patch a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patch a user with a given id"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @PatchMapping(path = ID)
    public UserDto patchUser(@ApiParam(value = "User id", required = true) @PathVariable Long id, @ApiParam(value = "Patch request", required = true) @RequestBody Map<String, Object> patch) {
        return userService.patchUser(id, patch);
    }

    /**
     * Add cities to an user.
     *
     * @param id       the id of the user
     * @param citiesId the id list of the cities to add to the user
     * @return the updated user
     */
    @ApiOperation("Add cities to an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cities added to the user with a given id"),
            @ApiResponse(code = 404, message = "User or city not found")
    })
    @PostMapping(path = ID + CITIES)
    public UserDto addCities(@ApiParam(value = "User id", required = true) @PathVariable Long id, @ApiParam(value = "Cities Id", required = true) @RequestBody List<Long> citiesId) {
        return userService.addCities(id, citiesId);
    }

    /**
     * Add departments to an user.
     *
     * @param id            the id of the user
     * @param departmentsId the id list of the departments to add to the user
     * @return the updated user
     */
    @ApiOperation("Add departments to an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Departments added to the user with a given id"),
            @ApiResponse(code = 404, message = "User or department not found")
    })
    @PostMapping(path = ID + DEPARTMENTS)
    public UserDto addDepartments(@ApiParam(value = "User id", required = true) @PathVariable Long id, @ApiParam(value = "Departments Id", required = true) @RequestBody List<Long> departmentsId) {
        return userService.addDepartments(id, departmentsId);
    }

    /**
     * Add regions to an user.
     *
     * @param id        the id of the user
     * @param regionsId the id list of the regions to add to the user
     * @return the updated user
     */
    @ApiOperation("Add regions to an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Regions added to the user with a given id"),
            @ApiResponse(code = 404, message = "User or region not found")
    })
    @PostMapping(path = ID + REGIONS)
    public UserDto addRegions(@ApiParam(value = "User id", required = true) @PathVariable Long id, @ApiParam(value = "Regions Id", required = true) @RequestBody List<Long> regionsId) {
        return userService.addRegions(id, regionsId);
    }

    /**
     * Add activities to an user.
     *
     * @param id           the id of the user
     * @param activitiesId the id list of the activities to add to the user
     * @return the updated user
     */
    @ApiOperation("Add activities to an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Activities added to the user with a given id"),
            @ApiResponse(code = 404, message = "User or activitie not found")
    })
    @PostMapping(path = ID + ACTIVITIES)
    public UserDto addActivities(@ApiParam(value = "User id", required = true) @PathVariable Long id, @ApiParam(value = "Activities Id", required = true) @RequestBody List<Long> activitiesId) {
        return userService.addActivities(id, activitiesId);
    }

    /**
     * Add events to an user.
     *
     * @param id       the id of the user
     * @param eventsId the id list of the events to add to the user
     * @return the updated user
     */
    @ApiOperation("Add events to an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Events added to the user with a given id"),
            @ApiResponse(code = 404, message = "User or event not found")
    })
    @PostMapping(path = ID + EVENTS)
    public UserDto addEvents(@ApiParam(value = "User id", required = true) @PathVariable Long id, @ApiParam(value = "Events Id", required = true) @RequestBody List<Long> eventsId) {
        return userService.addEvents(id, eventsId);
    }


    /**
     * Remove cities to an user.
     *
     * @param id       the id of the user
     * @param citiesId the id list of the cities to remove from the user
     * @return the updated user
     */
    @ApiOperation("Remove cities to an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cities removed to the user with a given id"),
            @ApiResponse(code = 404, message = "User or city not found")
    })
    @DeleteMapping(path = ID + CITIES)
    public UserDto removeCities(@ApiParam(value = "User id", required = true) @PathVariable Long id, @ApiParam(value = "Cities Id", required = true) @RequestBody List<Long> citiesId) {
        return userService.removeCities(id, citiesId);
    }

    /**
     * Remove departments to an user.
     *
     * @param id            the id of the user
     * @param departmentsId the id list of the departments to remove from the user
     * @return the updated user
     */
    @ApiOperation("Remove departments to an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Departments removed to the user with a given id"),
            @ApiResponse(code = 404, message = "User or department not found")
    })
    @DeleteMapping(path = ID + DEPARTMENTS)
    public UserDto removeDepartments(@ApiParam(value = "User id", required = true) @PathVariable Long id, @ApiParam(value = "Departments Id", required = true) @RequestBody List<Long> departmentsId) {
        return userService.removeDepartments(id, departmentsId);
    }

    /**
     * Remove regions to an user.
     *
     * @param id        the id of the user
     * @param regionsId the id list of the regions to remove from the user
     * @return the updated user
     */
    @ApiOperation("Remove regions to an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Regions removed to the user with a given id"),
            @ApiResponse(code = 404, message = "User or region not found")
    })
    @DeleteMapping(path = ID + REGIONS)
    public UserDto removeRegions(@ApiParam(value = "User id", required = true) @PathVariable Long id, @ApiParam(value = "Regions Id", required = true) @RequestBody List<Long> regionsId) {
        return userService.removeRegions(id, regionsId);
    }

    /**
     * Remove activities to an user.
     *
     * @param id           the id of the user
     * @param activitiesId the id list of the activities to remove from the user
     * @return the updated user
     */
    @ApiOperation("Remove activities to an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Activities removed to the user with a given id"),
            @ApiResponse(code = 404, message = "User or activity not found")
    })
    @DeleteMapping(path = ID + ACTIVITIES)
    public UserDto removeActivities(@ApiParam(value = "User id", required = true) @PathVariable Long id, @ApiParam(value = "Activities Id", required = true) @RequestBody List<Long> activitiesId) {
        return userService.removeActivities(id, activitiesId);
    }

    /**
     * Remove events to an user.
     *
     * @param id       the id of the user
     * @param eventsId the id list of the events to remove from the user
     * @return the updated user
     */
    @ApiOperation("Remove events to an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Events removed to the user with a given id"),
            @ApiResponse(code = 404, message = "User or event not found")
    })
    @DeleteMapping(path = ID + EVENTS)
    public UserDto removeEvents(@ApiParam(value = "User id", required = true) @PathVariable Long id, @ApiParam(value = "Events Id", required = true) @RequestBody List<Long> eventsId) {
        return userService.removeEvents(id, eventsId);
    }
}
