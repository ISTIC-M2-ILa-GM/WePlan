package fr.istic.gm.weplan.server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static fr.istic.gm.weplan.server.TestData.EMAIL;
import static fr.istic.gm.weplan.server.TestData.SOME_STRING;
import static fr.istic.gm.weplan.server.TestData.someUser;
import static fr.istic.gm.weplan.server.config.consts.ApiParams.USERNAME;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RunWith(MockitoJUnitRunner.class)
public class AjaxAuthSuccessHandlerTest {

    private AjaxAuthSuccessHandler ajaxAuthSuccessHandler;

    @Mock
    private UserService mockUserService;

    @Mock
    private ObjectMapper mockObjectMapper;

    @Before
    public void setUp() {
        ajaxAuthSuccessHandler = new AjaxAuthSuccessHandler(mockUserService, mockObjectMapper);
    }

    @Test
    public void shouldRespondSuccess() throws IOException {

        UserDto user = someUser();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter(USERNAME, EMAIL);

        when(mockUserService.getUserByEmail(anyString())).thenReturn(user);
        when(mockObjectMapper.writeValueAsString(any())).thenReturn(SOME_STRING);

        ajaxAuthSuccessHandler.onAuthenticationSuccess(request, response, null);

        verify(mockUserService).getUserByEmail(EMAIL);

        assertThat(response, notNullValue());
        assertThat(response.getHeader(CONTENT_TYPE), equalTo(APPLICATION_JSON_VALUE));
        assertThat(response.getStatus(), equalTo(HttpServletResponse.SC_OK));
        assertThat(response.getContentAsString(), equalTo(SOME_STRING));
    }
}
