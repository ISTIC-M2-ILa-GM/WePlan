package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.UserAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.RoleDto;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.entities.Event;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.entities.Role;
import fr.istic.gm.weplan.domain.model.entities.User;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.model.request.UserRequest;
import fr.istic.gm.weplan.domain.service.ActivityDaoService;
import fr.istic.gm.weplan.domain.service.CityDaoService;
import fr.istic.gm.weplan.domain.service.DepartmentDaoService;
import fr.istic.gm.weplan.domain.service.EventDaoService;
import fr.istic.gm.weplan.domain.service.RegionDaoService;
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

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.TestData.EMAIL;
import static fr.istic.gm.weplan.domain.TestData.ID;
import static fr.istic.gm.weplan.domain.TestData.someActivity;
import static fr.istic.gm.weplan.domain.TestData.someCity;
import static fr.istic.gm.weplan.domain.TestData.someDepartment;
import static fr.istic.gm.weplan.domain.TestData.someEvent;
import static fr.istic.gm.weplan.domain.TestData.somePageOptions;
import static fr.istic.gm.weplan.domain.TestData.someRegion;
import static fr.istic.gm.weplan.domain.TestData.someUser;
import static fr.istic.gm.weplan.domain.TestData.someUserRequest;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOTHING_TO_PATCH;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static fr.istic.gm.weplan.domain.exception.DomainException.WRONG_DATA_TO_PATCH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserServiceImpl service;

    @Mock
    private UserAdapter mockUserAdapter;

    @Mock
    private CityDaoService mockCityDaoService;

    @Mock
    private DepartmentDaoService mockDepartmentDaoService;

    @Mock
    private RegionDaoService mockRegionDaoService;

    @Mock
    private ActivityDaoService mockActivityDaoService;

    @Mock
    private EventDaoService mockEventDaoService;

    @Mock
    private Clock mockClock;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private PersistenceMapper persistenceMapper;
    private Instant now;

    @Before
    public void setUp() {
        persistenceMapper = Mappers.getMapper(PersistenceMapper.class);
        service = new UserServiceImpl(
                mockUserAdapter,
                mockCityDaoService,
                mockDepartmentDaoService,
                mockRegionDaoService,
                mockActivityDaoService,
                mockEventDaoService,
                persistenceMapper,
                mockClock
        );

        stub();
    }

    private void stub() {
        when(mockUserAdapter.save(any())).thenAnswer(i -> i.getArguments()[0]);

        now = Instant.now();
        when(mockClock.instant()).thenReturn(now);
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

        PageRequest pageRequest = somePageOptions();
        Page<User> users = new PageImpl<>(Collections.singletonList(someUser()), org.springframework.data.domain.PageRequest.of(1, 1), 2);

        when(mockUserAdapter.findAllByDeletedAtIsNull(any())).thenReturn(users);

        PageDto<UserDto> results = service.getUsers(pageRequest);

        org.springframework.data.domain.PageRequest expectedPageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getSize());

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

        User user = someUser();
        Optional<User> optionalUser = Optional.of(user);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

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

    @Test
    public void shouldAddCitiesToAnUser() {

        City city = someCity();
        User user = someUser();
        user.setCities(new ArrayList<>());
        Optional<User> optionalUser = Optional.of(user);
        List<Long> citiesId = new ArrayList<>();
        citiesId.add(ID + 1);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);
        when(mockCityDaoService.getCityDao(any())).thenReturn(city);

        UserDto userDto = service.addCities(ID, citiesId);

        verify(mockUserAdapter).findById(ID);
        verify(mockCityDaoService).getCityDao(ID + 1);
        verify(mockUserAdapter).save(user);

        assertThat(user, notNullValue());
        assertThat(user.getCities(), notNullValue());
        assertThat(user.getCities(), hasSize(1));
        assertThat(user.getCities().get(0), equalTo(city));
        assertThat(userDto, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldNotAddACityToAnUserWhichIsAlreadyIn() {

        City city = someCity();
        User user = someUser();
        user.setCities(new ArrayList<>());
        user.getCities().add(city);
        Optional<User> optionalUser = Optional.of(user);
        List<Long> citiesId = new ArrayList<>();
        citiesId.add(ID + 1);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);
        when(mockCityDaoService.getCityDao(any())).thenReturn(city);

        UserDto userDto = service.addCities(ID, citiesId);

        verify(mockUserAdapter).findById(ID);
        verify(mockCityDaoService).getCityDao(ID + 1);
        verify(mockUserAdapter).save(user);

        assertThat(user, notNullValue());
        assertThat(user.getCities(), notNullValue());
        assertThat(user.getCities(), hasSize(1));
        assertThat(user.getCities().get(0), equalTo(city));
        assertThat(userDto, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenAddCitiesToANullUser() {

        when(mockUserAdapter.findById(any())).thenReturn(Optional.empty());

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.addCities(ID, new ArrayList<>());
    }

    @Test
    public void shouldAddDepartmentsToAnUser() {

        Department department = someDepartment();
        User user = someUser();
        user.setDepartments(new ArrayList<>());
        Optional<User> optionalUser = Optional.of(user);
        List<Long> departmentsId = new ArrayList<>();
        departmentsId.add(ID + 1);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);
        when(mockDepartmentDaoService.getDepartmentDao(any())).thenReturn(department);

        UserDto userDto = service.addDepartments(ID, departmentsId);

        verify(mockUserAdapter).findById(ID);
        verify(mockDepartmentDaoService).getDepartmentDao(ID + 1);
        verify(mockUserAdapter).save(user);

        assertThat(user, notNullValue());
        assertThat(user.getDepartments(), notNullValue());
        assertThat(user.getDepartments(), hasSize(1));
        assertThat(user.getDepartments().get(0), equalTo(department));
        assertThat(userDto, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldNotAddADepartmentToAnUserWhichIsAlreadyIn() {

        Department department = someDepartment();
        User user = someUser();
        user.setDepartments(new ArrayList<>());
        user.getDepartments().add(department);
        Optional<User> optionalUser = Optional.of(user);
        List<Long> departmentsId = new ArrayList<>();
        departmentsId.add(ID + 1);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);
        when(mockDepartmentDaoService.getDepartmentDao(any())).thenReturn(department);

        UserDto userDto = service.addDepartments(ID, departmentsId);

        verify(mockUserAdapter).findById(ID);
        verify(mockDepartmentDaoService).getDepartmentDao(ID + 1);
        verify(mockUserAdapter).save(user);

        assertThat(user, notNullValue());
        assertThat(user.getDepartments(), notNullValue());
        assertThat(user.getDepartments(), hasSize(1));
        assertThat(user.getDepartments().get(0), equalTo(department));
        assertThat(userDto, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenAddDepartmentsToANullUser() {

        when(mockUserAdapter.findById(any())).thenReturn(Optional.empty());

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.addDepartments(ID, new ArrayList<>());
    }

    @Test
    public void shouldAddRegionsToAnUser() {

        Region region = someRegion();
        User user = someUser();
        user.setRegions(new ArrayList<>());
        Optional<User> optionalUser = Optional.of(user);
        List<Long> regionsId = new ArrayList<>();
        regionsId.add(ID + 1);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);
        when(mockRegionDaoService.getRegionDao(any())).thenReturn(region);

        UserDto userDto = service.addRegions(ID, regionsId);

        verify(mockUserAdapter).findById(ID);
        verify(mockRegionDaoService).getRegionDao(ID + 1);
        verify(mockUserAdapter).save(user);

        assertThat(user, notNullValue());
        assertThat(user.getRegions(), notNullValue());
        assertThat(user.getRegions(), hasSize(1));
        assertThat(user.getRegions().get(0), equalTo(region));
        assertThat(userDto, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldNotAddARegionToAnUserWhichIsAlreadyIn() {

        Region region = someRegion();
        User user = someUser();
        user.setRegions(new ArrayList<>());
        user.getRegions().add(region);
        Optional<User> optionalUser = Optional.of(user);
        List<Long> regionsId = new ArrayList<>();
        regionsId.add(ID + 1);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);
        when(mockRegionDaoService.getRegionDao(any())).thenReturn(region);

        UserDto userDto = service.addRegions(ID, regionsId);

        verify(mockUserAdapter).findById(ID);
        verify(mockRegionDaoService).getRegionDao(ID + 1);
        verify(mockUserAdapter).save(user);

        assertThat(user, notNullValue());
        assertThat(user.getRegions(), notNullValue());
        assertThat(user.getRegions(), hasSize(1));
        assertThat(user.getRegions().get(0), equalTo(region));
        assertThat(userDto, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenAddRegionsToANullUser() {

        when(mockUserAdapter.findById(any())).thenReturn(Optional.empty());

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.addRegions(ID, new ArrayList<>());
    }

    @Test
    public void shouldAddActivitiesToAnUser() {

        Activity activity = someActivity();
        User user = someUser();
        user.setActivities(new ArrayList<>());
        Optional<User> optionalUser = Optional.of(user);
        List<Long> activitiesId = new ArrayList<>();
        activitiesId.add(ID + 1);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);
        when(mockActivityDaoService.getActivityDao(any())).thenReturn(activity);

        UserDto userDto = service.addActivities(ID, activitiesId);

        verify(mockUserAdapter).findById(ID);
        verify(mockActivityDaoService).getActivityDao(ID + 1);
        verify(mockUserAdapter).save(user);

        assertThat(user, notNullValue());
        assertThat(user.getActivities(), notNullValue());
        assertThat(user.getActivities(), hasSize(1));
        assertThat(user.getActivities().get(0), equalTo(activity));
        assertThat(userDto, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldNotAddAnActivityToAnUserWhichIsAlreadyIn() {

        Activity activity = someActivity();
        User user = someUser();
        user.setActivities(new ArrayList<>());
        user.getActivities().add(activity);
        Optional<User> optionalUser = Optional.of(user);
        List<Long> activitiesId = new ArrayList<>();
        activitiesId.add(ID + 1);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);
        when(mockActivityDaoService.getActivityDao(any())).thenReturn(activity);

        UserDto userDto = service.addActivities(ID, activitiesId);

        verify(mockUserAdapter).findById(ID);
        verify(mockActivityDaoService).getActivityDao(ID + 1);
        verify(mockUserAdapter).save(user);

        assertThat(user, notNullValue());
        assertThat(user.getActivities(), notNullValue());
        assertThat(user.getActivities(), hasSize(1));
        assertThat(user.getActivities().get(0), equalTo(activity));
        assertThat(userDto, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenAddActivitiesToANullUser() {

        when(mockUserAdapter.findById(any())).thenReturn(Optional.empty());

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.addActivities(ID, new ArrayList<>());
    }

    @Test
    public void shouldAddEventsToAnUser() {

        Event event = someEvent();
        User user = someUser();
        user.setEvents(new ArrayList<>());
        Optional<User> optionalUser = Optional.of(user);
        List<Long> eventsId = new ArrayList<>();
        eventsId.add(ID + 1);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);
        when(mockEventDaoService.getEventDao(any())).thenReturn(event);

        UserDto userDto = service.addEvents(ID, eventsId);

        verify(mockUserAdapter).findById(ID);
        verify(mockEventDaoService).getEventDao(ID + 1);
        verify(mockUserAdapter).save(user);

        assertThat(user, notNullValue());
        assertThat(user.getEvents(), notNullValue());
        assertThat(user.getEvents(), hasSize(1));
        assertThat(user.getEvents().get(0), equalTo(event));
        assertThat(userDto, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldNotAddAnEventToAnUserWhichIsAlreadyIn() {

        Event event = someEvent();
        User user = someUser();
        user.setEvents(new ArrayList<>());
        user.getEvents().add(event);
        Optional<User> optionalUser = Optional.of(user);
        List<Long> eventsId = new ArrayList<>();
        eventsId.add(ID + 1);

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);
        when(mockEventDaoService.getEventDao(any())).thenReturn(event);

        UserDto userDto = service.addEvents(ID, eventsId);

        verify(mockUserAdapter).findById(ID);
        verify(mockEventDaoService).getEventDao(ID + 1);
        verify(mockUserAdapter).save(user);

        assertThat(user, notNullValue());
        assertThat(user.getEvents(), notNullValue());
        assertThat(user.getEvents(), hasSize(1));
        assertThat(user.getEvents().get(0), equalTo(event));
        assertThat(userDto, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenAddEventsToANullUser() {

        when(mockUserAdapter.findById(any())).thenReturn(Optional.empty());

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.addEvents(ID, new ArrayList<>());
    }

    @Test
    public void shouldRemoveCitiesToAnUser() {

        City city = someCity();
        User user = someUser();
        user.setCities(new ArrayList<>());
        user.getCities().add(city);
        user.getCities().add(someCity());
        Optional<User> optionalUser = Optional.of(user);
        List<Long> citiesId = new ArrayList<>();
        citiesId.add(city.getId());

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        UserDto userDto = service.removeCities(ID, citiesId);

        verify(mockUserAdapter).findById(ID);
        verify(mockUserAdapter).save(user);

        assertThat(user, notNullValue());
        assertThat(user.getCities(), notNullValue());
        assertThat(user.getCities(), hasSize(1));
        assertThat(user.getCities().get(0), not(equalTo(city)));
        assertThat(userDto, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenRemoveCitiesToANullUser() {

        when(mockUserAdapter.findById(any())).thenReturn(Optional.empty());

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.removeCities(ID, new ArrayList<>());
    }

    @Test
    public void shouldRemoveDepartmentsToAnUser() {

        Department department = someDepartment();
        User user = someUser();
        user.setDepartments(new ArrayList<>());
        user.getDepartments().add(department);
        user.getDepartments().add(someDepartment());
        Optional<User> optionalUser = Optional.of(user);
        List<Long> departmentsId = new ArrayList<>();
        departmentsId.add(department.getId());

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        UserDto userDto = service.removeDepartments(ID, departmentsId);

        verify(mockUserAdapter).findById(ID);
        verify(mockUserAdapter).save(user);

        assertThat(user, notNullValue());
        assertThat(user.getDepartments(), notNullValue());
        assertThat(user.getDepartments(), hasSize(1));
        assertThat(user.getDepartments().get(0), not(equalTo(department)));
        assertThat(userDto, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenRemoveDepartmentsToANullUser() {

        when(mockUserAdapter.findById(any())).thenReturn(Optional.empty());

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.removeDepartments(ID, new ArrayList<>());
    }


    @Test
    public void shouldRemoveRegionsToAnUser() {

        Region region = someRegion();
        User user = someUser();
        user.setRegions(new ArrayList<>());
        user.getRegions().add(region);
        user.getRegions().add(someRegion());
        Optional<User> optionalUser = Optional.of(user);
        List<Long> regionsId = new ArrayList<>();
        regionsId.add(region.getId());

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        UserDto userDto = service.removeRegions(ID, regionsId);

        verify(mockUserAdapter).findById(ID);
        verify(mockUserAdapter).save(user);

        assertThat(user, notNullValue());
        assertThat(user.getRegions(), notNullValue());
        assertThat(user.getRegions(), hasSize(1));
        assertThat(user.getRegions().get(0), not(equalTo(region)));
        assertThat(userDto, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenRemoveRegionsToANullUser() {

        when(mockUserAdapter.findById(any())).thenReturn(Optional.empty());

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.removeRegions(ID, new ArrayList<>());
    }

    @Test
    public void shouldRemoveActivitiesToAnUser() {

        Activity activity = someActivity();
        User user = someUser();
        user.setActivities(new ArrayList<>());
        user.getActivities().add(activity);
        user.getActivities().add(someActivity());
        Optional<User> optionalUser = Optional.of(user);
        List<Long> activitiesId = new ArrayList<>();
        activitiesId.add(activity.getId());

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        UserDto userDto = service.removeActivities(ID, activitiesId);

        verify(mockUserAdapter).findById(ID);
        verify(mockUserAdapter).save(user);

        assertThat(user, notNullValue());
        assertThat(user.getActivities(), notNullValue());
        assertThat(user.getActivities(), hasSize(1));
        assertThat(user.getActivities().get(0), not(equalTo(activity)));
        assertThat(userDto, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenRemoveActivitiesToANullUser() {

        when(mockUserAdapter.findById(any())).thenReturn(Optional.empty());

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.removeActivities(ID, new ArrayList<>());
    }

    @Test
    public void shouldRemoveEventsToAnUser() {

        Event event = someEvent();
        User user = someUser();
        user.setEvents(new ArrayList<>());
        user.getEvents().add(event);
        user.getEvents().add(someEvent());
        Optional<User> optionalUser = Optional.of(user);
        List<Long> eventsId = new ArrayList<>();
        eventsId.add(event.getId());

        when(mockUserAdapter.findById(any())).thenReturn(optionalUser);

        UserDto userDto = service.removeEvents(ID, eventsId);

        verify(mockUserAdapter).findById(ID);
        verify(mockUserAdapter).save(user);

        assertThat(user, notNullValue());
        assertThat(user.getEvents(), notNullValue());
        assertThat(user.getEvents(), hasSize(1));
        assertThat(user.getEvents().get(0), not(equalTo(event)));
        assertThat(userDto, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenRemoveEventsToANullUser() {

        when(mockUserAdapter.findById(any())).thenReturn(Optional.empty());

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.removeEvents(ID, new ArrayList<>());
    }

    @Test
    public void shouldGetUserDaoByEmail() {

        User user = someUser();
        Optional<User> optionalUser = Optional.of(user);

        when(mockUserAdapter.findOneByEmail(anyString())).thenReturn(optionalUser);

        User result = service.getUserDaoByEmail(EMAIL);

        verify(mockUserAdapter).findOneByEmail(EMAIL);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(user));
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetANullUserDaoByEmail() {

        when(mockUserAdapter.findOneByEmail(any())).thenReturn(Optional.empty());

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.getUserDaoByEmail(EMAIL);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetADeletedUserDaoByEmail() {

        User user = someUser();
        user.setDeletedAt(Instant.now());
        Optional<User> optionalUser = Optional.of(user);

        when(mockUserAdapter.findOneByEmail(anyString())).thenReturn(optionalUser);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.getUserDaoByEmail(EMAIL);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetAnUserDaoByEmailWithNullEmail() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.getUserDaoByEmail(null);
    }

    @Test
    public void shouldGetUserDtoByEmail() {

        User user = someUser();
        Optional<User> optionalUser = Optional.of(user);

        when(mockUserAdapter.findOneByEmail(any())).thenReturn(optionalUser);

        UserDto results = service.getUserByEmail(EMAIL);

        verify(mockUserAdapter).findOneByEmail(EMAIL);

        assertThat(results, notNullValue());
        assertThat(results, equalTo(persistenceMapper.toUserDto(user)));
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetANullUserByEmail() {

        Optional<User> optionalUser = Optional.empty();

        when(mockUserAdapter.findOneByEmail(any())).thenReturn(optionalUser);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.getUserByEmail(EMAIL);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetADeletedUserByEmail() {

        User user = someUser();
        user.setDeletedAt(Instant.now());
        Optional<User> optionalUser = Optional.of(user);

        when(mockUserAdapter.findOneByEmail(any())).thenReturn(optionalUser);

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.getUserByEmail(EMAIL);
    }

    @Test
    public void shouldThrowDomainExceptionWhenGetAUserByEmailWithNullEmail() {

        thrown.expect(DomainException.class);
        thrown.expectMessage(String.format(NOT_FOUND_MSG, User.class.getSimpleName()));

        service.getUserByEmail(null);
    }
}
