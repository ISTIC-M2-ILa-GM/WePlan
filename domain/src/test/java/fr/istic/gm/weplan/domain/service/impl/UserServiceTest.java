package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.UserAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RoleDto;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.model.entities.Role;
import fr.istic.gm.weplan.domain.model.entities.User;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.UserRequest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Clock;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.TestData.ID;
import static fr.istic.gm.weplan.domain.TestData.somePageOptions;
import static fr.istic.gm.weplan.domain.TestData.someUser;
import static fr.istic.gm.weplan.domain.TestData.someUserRequest;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOTHING_TO_PATCH;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static fr.istic.gm.weplan.domain.exception.DomainException.WRONG_DATA_TO_PATCH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserServiceImpl service;

    @Mock
    private UserAdapter mockUserAdapter;

    @Mock
    private Clock mockClock;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private PersistenceMapper persistenceMapper;

    @Before
    public void setUp() {
        persistenceMapper = Mappers.getMapper(PersistenceMapper.class);
        service = new UserServiceImpl(mockUserAdapter, persistenceMapper, mockClock);

        stub();
    }

    private void stub() {
        when(mockUserAdapter.save(any())).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    public void shouldGetUserDao() {

        User user = someUser();
        Optional<User> optionalUser = Optional.of(user);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        User result = service.getUserDao(ID);

        verify(mockUserAdapter).findById(ID);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(user));
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetANullDepartmentDao() {

        Optional<User> optionalUser = Optional.empty();

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.getUserDao(ID);
    }

    @Test
    public void shouldGetUsers() {

        PageOptions pageOptions = somePageOptions();
        Page<User> users = new PageImpl<>(Collections.singletonList(someUser()), PageRequest.of(1, 1), 2);

        when(mockUserAdapter.findAllByDeletedAtIsNull(any())).thenReturn(users);

        PageDto<UserDto> results = service.getUsers(pageOptions);

        PageRequest expectedPageable = PageRequest.of(pageOptions.getPage(), pageOptions.getSize());

        verify(mockUserAdapter).findAllByDeletedAtIsNull(expectedPageable);

        assertThat(results, notNullValue());
        assertThat(results.getResults(), equalTo(persistenceMapper.toUsersDto(users.getContent())));
        assertThat(results.getTotalPages(), equalTo(2));
        assertThat(results.getSize(), equalTo(1));
    }

    @Test
    public void shouldGetUser() {

        User user = someUser();
        Optional<User> optionalUser = Optional.of(user);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        UserDto results = service.getUser(ID);

        verify(mockUserAdapter).findById(ID);

        assertThat(results, notNullValue());
        assertThat(results, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetANullUser() {

        Optional<User> optionalUser = Optional.empty();

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.getUser(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetADeletedUser() {

        User user = someUser();
        user.setDeletedAt(Instant.now());
        Optional<User> optionalUser = Optional.of(user);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.getUser(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetAUserWithNullId() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.getUser(null);
    }

    @Test
    public void shouldCreateAUser() {

        UserRequest userRequest = someUserRequest();

        UserDto results = service.createUser(userRequest);

        User expectedUser = persistenceMapper.toUser(userRequest);
        expectedUser.setRole(Role.USER);

        verify(mockUserAdapter).save(expectedUser);

        assertThat(results, notNullValue());
        assertThat(results.getEmail(), equalTo(userRequest.getEmail()));
        assertThat(results.getFirstName(), equalTo(userRequest.getFirstName()));
        assertThat(results.getLastName(), equalTo(userRequest.getLastName()));
        assertThat(results.getRole(), equalTo(RoleDto.USER));
    }

    @Test
    public void shouldDeleteAUser() {

        Instant now = Instant.now();
        User user = someUser();
        Optional<User> optionalUser = Optional.of(user);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);
        when(mockClock.instant()).thenReturn(now);

        service.deleteUser(ID);

        verify(mockUserAdapter).findById(ID);
        verify(mockUserAdapter).save(user);
        verify(mockClock).instant();

        assertThat(user.getDeletedAt(), notNullValue());
        assertThat(user.getDeletedAt(), equalTo(now));
    }

    @Test
    public void shouldThrowDomainExceptionWhenDeleteANullUser() {

        Optional<User> optionalUser = Optional.empty();

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.deleteUser(ID);
    }

    @Test
    public void shouldThrowDomainExceptionWhenDeleteADeletedUser() {

        User user = someUser();
        user.setDeletedAt(Instant.now());
        Optional<User> optionalUser = Optional.of(user);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.deleteUser(ID);
    }

    @Test
    public void shouldPatchAUser() {

        User user = someUser();
        user.setId(ID);
        Optional<User> optionalUser = Optional.of(user);

        Map<String, Object> patch = new HashMap<>();
        patch.put("firstName", "a-new-name");
        patch.put("id", 18);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        UserDto result = service.patchUser(ID, patch);

        verify(mockUserAdapter).findById(ID);
        verify(mockUserAdapter).save(user);

        assertThat(result, notNullValue());
        assertThat(result.getId(), equalTo(ID));
        assertThat(result.getFirstName(), equalTo("a-new-name"));
    }

    @Test
    public void shouldThrowAnExceptionWhenPatchAUserWithForbiddenData() {

        User user = someUser();
        user.setRole(Role.USER);
        user.setId(ID);
        Optional<User> optionalUser = Optional.of(user);

        Map<String, Object> patch = new HashMap<>();
        patch.put("role", Role.ADMIN);
        patch.put("id", 18);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        service.patchUser(ID, patch);

        assertThat(user.getRole(), equalTo(Role.USER));
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchADeletedUser() {

        User user = someUser();
        user.setDeletedAt(Instant.now());
        Optional<User> optionalUser = Optional.of(user);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.patchUser(ID, new HashMap<>());
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchANullUser() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.patchUser(null, new HashMap<>());
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchAUserWithANullPatch() {

        User user = someUser();
        user.setDeletedAt(null);
        Optional<User> optionalUser = Optional.of(user);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        thrown.expect(DomainException.class);
        thrown.expectMessage(NOTHING_TO_PATCH);

        service.patchUser(ID, null);
    }

    @Test
    public void shouldThrowDomainExceptionWhenPatchAUserWithAnEmptyPatch() {

        User user = someUser();
        user.setDeletedAt(null);
        Optional<User> optionalUser = Optional.of(user);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        thrown.expect(DomainException.class);
        thrown.expectMessage(NOTHING_TO_PATCH);

        service.patchUser(ID, new HashMap<>());
    }

    @Test
    public void shouldThrowExceptionWhenPatchAUserWithWrongType() {

        User user = someUser();
        user.setDeletedAt(null);
        Optional<User> optionalUser = Optional.of(user);

        Map<String, Object> patch = new HashMap<>();
        patch.put("firstName", new Object());

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        thrown.expect(DomainException.class);
        thrown.expectMessage(WRONG_DATA_TO_PATCH);

        service.patchUser(ID, patch);
    }
}
