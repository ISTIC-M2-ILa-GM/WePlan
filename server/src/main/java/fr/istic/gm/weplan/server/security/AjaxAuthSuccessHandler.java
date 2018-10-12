package fr.istic.gm.weplan.server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static fr.istic.gm.weplan.server.config.consts.ApiParams.EMAIL;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The ajax authentication success handler
 */
@AllArgsConstructor
@Component
public class AjaxAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private UserService userService;

    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        UserDto user = userService.getUserByEmail(request.getParameter(EMAIL));
        String json = objectMapper.writeValueAsString(user);
        response.setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        response.getWriter().print(json);
        response.getWriter().flush();
    }
}
