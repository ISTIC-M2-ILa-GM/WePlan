package fr.istic.gm.weplan.server.service.impl;

import fr.istic.gm.weplan.server.component.AuthenticationFacade;
import fr.istic.gm.weplan.server.exception.ServerException;
import fr.istic.gm.weplan.server.model.SecurityUser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;

import static fr.istic.gm.weplan.server.TestData.ID;
import static fr.istic.gm.weplan.server.service.impl.AuthService.NO_LOGGED_USER;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    private AuthService authService;

    @Mock
    private AuthenticationFacade mockAuthenticationFacade;

    @Mock
    private Authentication mockAuthentication;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {

        authService = new AuthService(mockAuthenticationFacade);

        when(mockAuthenticationFacade.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(new SecurityUser(ID, "an-email", "a-password", new ArrayList<>()));
    }

    @Test
    public void shouldLoggedUser() {

        Long result = authService.loggedUserId();

        verify(mockAuthentication, times(3)).getPrincipal();

        assertThat(result, notNullValue());
        assertThat(result, equalTo(ID));
    }

    @Test
    public void shouldThrowExceptionWhenLoggedUserWithNullAuth() {

        when(mockAuthenticationFacade.getAuthentication()).thenReturn(null);

        authService = new AuthService(mockAuthenticationFacade);

        thrown.expect(ServerException.class);
        thrown.expectMessage(NO_LOGGED_USER);

        authService.loggedUserId();
    }

    @Test
    public void shouldThrowExceptionWhenLoggedUserWithNullPrincipal() {

        when(mockAuthentication.getPrincipal()).thenReturn(null);

        thrown.expect(ServerException.class);
        thrown.expectMessage(NO_LOGGED_USER);

        authService.loggedUserId();
    }

    @Test
    public void shouldThrowExceptionWhenLoggedUserWithNotASecurityUser() {

        when(mockAuthentication.getPrincipal()).thenReturn(new Object());

        thrown.expect(ServerException.class);
        thrown.expectMessage(NO_LOGGED_USER);

        authService.loggedUserId();
    }
}
