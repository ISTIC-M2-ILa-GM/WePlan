package fr.istic.gm.weplan.server.controller;


import fr.istic.gm.weplan.domain.adapter.AuthAdapter;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.AUTH;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.USER_CURRENT;

@Api(tags = "Auth Controller")
@AllArgsConstructor
@RestController
@RequestMapping(path = AUTH, produces = "application/json")
public class AuthController {

    private AuthAdapter authAdapter;

    private UserService userService;

    @ApiOperation("Retrieve current user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Logged-in user")
    })
    @GetMapping(path = USER_CURRENT, produces = "application/json")
    public UserDto currentUser() {
        return userService.getUser(authAdapter.loggedUserId());
    }
}
