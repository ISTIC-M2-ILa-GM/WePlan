package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.model.request.UserRequest;
import fr.istic.gm.weplan.domain.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static fr.istic.gm.weplan.server.TestData.ID;
import static fr.istic.gm.weplan.server.TestData.somePageOptions;
import static fr.istic.gm.weplan.server.TestData.somePageUsers;
import static fr.istic.gm.weplan.server.TestData.someUser;
import static fr.istic.gm.weplan.server.TestData.someUserRequest;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private UserController controller;

    @Mock
    private UserService mockUserService;

    @Before
    public void setUp() {
        controller = new UserController(mockUserService);
    }

    @Test
    public void shouldGetUsers() {

        PageOptions pageOptions = somePageOptions();
        PageDto<UserDto> pageUsers = somePageUsers();

        when(mockUserService.getUsers(any())).thenReturn(pageUsers);

        PageDto<UserDto> result = controller.getUsers(pageOptions);

        verify(mockUserService).getUsers(pageOptions);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(pageUsers));
    }

    @Test
    public void shouldGetAUser() {

        UserDto user = someUser();

        when(mockUserService.getUser(any())).thenReturn(user);

        UserDto result = controller.getUser(ID);

        verify(mockUserService).getUser(ID);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(user));
    }

    @Test
    public void shouldCreateAUser() {

        UserRequest userRequest = someUserRequest();
        UserDto userDto = someUser();

        when(mockUserService.createUser(any())).thenReturn(userDto);

        UserDto result = controller.createUser(userRequest);

        verify(mockUserService).createUser(userRequest);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(userDto));
    }

    @Test
    public void shouldDeleteAUser() {

        controller.deleteUser(ID);

        verify(mockUserService).deleteUser(ID);
    }

    @Test
    public void shouldPatchAUser() {

        Map<String, Object> patch = new HashMap<>();

        UserDto userDto = someUser();

        when(mockUserService.patchUser(any(), any())).thenReturn(userDto);

        UserDto result = controller.patchUser(ID, patch);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(userDto));
    }
}
