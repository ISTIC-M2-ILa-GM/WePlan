package fr.istic.gm.weplan.server.component;

import org.springframework.security.core.Authentication;

/**
 * The authentication facade
 */
public interface AuthenticationFacade {

    /**
     * Retrieve the authentication.
     *
     * @return the authentication
     */
    Authentication getAuthentication();

}
