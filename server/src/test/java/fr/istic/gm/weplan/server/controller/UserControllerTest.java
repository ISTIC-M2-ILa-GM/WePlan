package fr.istic.gm.weplan.server.controller;

import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.model.request.UserRequest;
import fr.istic.gm.weplan.domain.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        PageRequest pageRequest = somePageOptions();
        PageDto<UserDto> pageUsers = somePageUsers();

        when(mockUserService.getUsers(any())).thenReturn(pageUsers);

        PageDto<UserDto> result = controller.getUsers(pageRequest);

        verify(mockUserService).getUsers(pageRequest);

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

        verify(mockUserService).patchUser(ID, patch);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(userDto));
    }

    @Test
    public void shouldAddCitiesToAnUser() {

        List<Long> citiesId = new ArrayList<>();
        citiesId.add(ID + 1);
        UserDto userDto = someUser();

        when(mockUserService.addCities(any(), any())).thenReturn(userDto);

        UserDto result = controller.addCities(ID, citiesId);

        verify(mockUserService).addCities(ID, citiesId);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(userDto));
    }

    @Test
    public void shouldAddDepartmentsToAnUser() {

        List<Long> departmentsId = new ArrayList<>();
        departmentsId.add(ID + 1);
        UserDto userDto = someUser();

        when(mockUserService.addDepartments(any(), any())).thenReturn(userDto);

        UserDto result = controller.addDepartments(ID, departmentsId);

        verify(mockUserService).addDepartments(ID, departmentsId);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(userDto));
    }

    @Test
    public void shouldAddRegionsToAnUser() {

        List<Long> regionsId = new ArrayList<>();
        regionsId.add(ID + 1);
        UserDto userDto = someUser();

        when(mockUserService.addRegions(any(), any())).thenReturn(userDto);

        UserDto result = controller.addRegions(ID, regionsId);

        verify(mockUserService).addRegions(ID, regionsId);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(userDto));
    }

    @Test
    public void shouldAddActivitiesToAnUser() {

        List<Long> activitiesId = new ArrayList<>();
        activitiesId.add(ID + 1);
        UserDto userDto = someUser();

        when(mockUserService.addActivities(any(), any())).thenReturn(userDto);

        UserDto result = controller.addActivities(ID, activitiesId);

        verify(mockUserService).addActivities(ID, activitiesId);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(userDto));
    }

    @Test
    public void shouldAddEventsToAnUser() {

        List<Long> eventsId = new ArrayList<>();
        eventsId.add(ID + 1);
        UserDto userDto = someUser();

        when(mockUserService.addEvents(any(), any())).thenReturn(userDto);

        UserDto result = controller.addEvents(ID, eventsId);

        verify(mockUserService).addEvents(ID, eventsId);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(userDto));
    }

    @Test
    public void shouldRemoveCitiesToAnUser() {

        List<Long> citiesId = new ArrayList<>();
        citiesId.add(ID + 1);
        UserDto userDto = someUser();

        when(mockUserService.removeCities(any(), any())).thenReturn(userDto);

        UserDto result = controller.removeCities(ID, citiesId);

        verify(mockUserService).removeCities(ID, citiesId);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(userDto));
    }

    @Test
    public void shouldRemoveDepartmentsToAnUser() {

        List<Long> departmentsId = new ArrayList<>();
        departmentsId.add(ID + 1);
        UserDto userDto = someUser();

        when(mockUserService.removeDepartments(any(), any())).thenReturn(userDto);

        UserDto result = controller.removeDepartments(ID, departmentsId);

        verify(mockUserService).removeDepartments(ID, departmentsId);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(userDto));
    }

    @Test
    public void shouldRemoveRegionsToAnUser() {

        List<Long> regionsId = new ArrayList<>();
        regionsId.add(ID + 1);
        UserDto userDto = someUser();

        when(mockUserService.removeRegions(any(), any())).thenReturn(userDto);

        UserDto result = controller.removeRegions(ID, regionsId);

        verify(mockUserService).removeRegions(ID, regionsId);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(userDto));
    }

    @Test
    public void shouldRemoveActivitiesToAnUser() {

        List<Long> activitiesId = new ArrayList<>();
        activitiesId.add(ID + 1);
        UserDto userDto = someUser();

        when(mockUserService.removeActivities(any(), any())).thenReturn(userDto);

        UserDto result = controller.removeActivities(ID, activitiesId);

        verify(mockUserService).removeActivities(ID, activitiesId);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(userDto));
    }

    @Test
    public void shouldRemoveEventsToAnUser() {

        List<Long> eventsId = new ArrayList<>();
        eventsId.add(ID + 1);
        UserDto userDto = someUser();

        when(mockUserService.removeEvents(any(), any())).thenReturn(userDto);

        UserDto result = controller.removeEvents(ID, eventsId);

        verify(mockUserService).removeEvents(ID, eventsId);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(userDto));
    }
}
