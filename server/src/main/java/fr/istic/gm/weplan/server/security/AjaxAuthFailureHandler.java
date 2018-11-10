package fr.istic.gm.weplan.server.security;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * The ajax authentication failure handler
 */
@Component
public class AjaxAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
}
