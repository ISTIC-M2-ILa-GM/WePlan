package fr.istic.gm.weplan.server.service.impl;

import fr.istic.gm.weplan.domain.adapter.AuthAdapter;
import fr.istic.gm.weplan.server.component.AuthenticationFacade;
import fr.istic.gm.weplan.server.exception.ServerException;
import fr.istic.gm.weplan.server.model.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.FORBIDDEN;

/**
 * The Auth Service
 */
@AllArgsConstructor
@Service
public class AuthService implements AuthAdapter {

    public static final String NO_LOGGED_USER = "No logged user";

    private AuthenticationFacade authenticationFacade;

    @Override
    public Long loggedUserId() {

        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof SecurityUser)) {
            throw new ServerException(NO_LOGGED_USER, "", FORBIDDEN);
        }
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return securityUser.getId();
    }
}
