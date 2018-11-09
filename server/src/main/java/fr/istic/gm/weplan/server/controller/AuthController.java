package fr.istic.gm.weplan.server.controller;


import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.service.UserService;
import fr.istic.gm.weplan.server.model.SecurityUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.AUTH;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.USER_CURRENT;

@Api(tags = "Auth Controller")
@AllArgsConstructor
@RestController
@RequestMapping(path = AUTH, produces = "application/json")
public class AuthController {
    private UserService userService;

    @ApiOperation("Retrieve current user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Logged-in user")
    })
    @GetMapping(path = USER_CURRENT, produces = "application/json")
    public UserDto currentUser(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal =  authentication.getPrincipal();
            if (principal instanceof SecurityUser) {
                SecurityUser securityUser = (SecurityUser) principal;
                return this.userService.getUser(securityUser.getId());
            }
        }
        return null;
    }
}
