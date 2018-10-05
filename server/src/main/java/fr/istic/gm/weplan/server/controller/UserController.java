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

import java.util.Map;

import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.CITY;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.ID;

/**
 * User Controller
 */
@Api(tags = "User Controller", description = "User API")
@AllArgsConstructor
@RestController
@RequestMapping(path = CITY, produces = "application/json")
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
}
