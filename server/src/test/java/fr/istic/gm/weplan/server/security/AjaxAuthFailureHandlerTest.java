package fr.istic.gm.weplan.server.security;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AjaxAuthFailureHandlerTest {

    private AjaxAuthFailureHandler ajaxAuthFailureHandler;

    @Before
    public void setUp() {
        ajaxAuthFailureHandler = new AjaxAuthFailureHandler();
    }

    @Test
    public void shouldSendAnAuthError() throws IOException, ServletException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        ajaxAuthFailureHandler.onAuthenticationFailure(request, response, null);

        assertThat(response, notNullValue());
        assertThat(response.getStatus(), equalTo(HttpServletResponse.SC_UNAUTHORIZED));
    }
}
