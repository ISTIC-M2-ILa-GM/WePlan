package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.adapter.AuthAdapter;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static fr.istic.gm.weplan.server.TestData.ID;
import static fr.istic.gm.weplan.server.TestData.someUser;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTest {

    private AuthController authController;

    @Mock
    private AuthAdapter mockAuthAdapter;

    @Mock
    private UserService mockUserService;

    @Before
    public void setUp() {
        authController = new AuthController(mockAuthAdapter, mockUserService);
    }

    @Test
    public void shouldGetCurrentUser() {

        UserDto userDto = someUser();

        when(mockAuthAdapter.loggedUserId()).thenReturn(ID);
        when(mockUserService.getUser(any())).thenReturn(userDto);

        UserDto result = authController.currentUser();

        verify(mockAuthAdapter).loggedUserId();

        assertThat(result, notNullValue());
        assertThat(result, equalTo(userDto));
    }
}
