package fr.istic.gm.weplan.domain.service.impl;

import fr.istic.gm.weplan.domain.adapter.UserAdapter;
import fr.istic.gm.weplan.domain.exception.DomainException;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.entities.Event;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.entities.Role;
import fr.istic.gm.weplan.domain.model.entities.User;
import fr.istic.gm.weplan.domain.model.mapper.PersistenceMapper;
import fr.istic.gm.weplan.domain.model.request.UserRequest;
import fr.istic.gm.weplan.domain.service.ActivityDaoService;
import fr.istic.gm.weplan.domain.service.CityDaoService;
import fr.istic.gm.weplan.domain.service.DepartmentDaoService;
import fr.istic.gm.weplan.domain.service.EventDaoService;
import fr.istic.gm.weplan.domain.service.RegionDaoService;
import fr.istic.gm.weplan.domain.service.UserDaoService;
import fr.istic.gm.weplan.domain.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static fr.istic.gm.weplan.domain.exception.DomainException.ExceptionType.NOT_FOUND;
import static fr.istic.gm.weplan.domain.exception.DomainException.NOT_FOUND_MSG;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Slf4j
@Service
public class UserServiceImpl extends PatchService<User> implements UserService, UserDaoService {

    private UserAdapter userAdapter;

    private CityDaoService cityDaoService;

    private DepartmentDaoService departmentDaoService;

    private RegionDaoService regionDaoService;

    private ActivityDaoService activityDaoService;

    private EventDaoService eventDaoService;

    private PersistenceMapper persistenceMapper;

    private Clock clock;

    @Override
    public User getUserDao(Long id) {

        Optional<User> user = id != null ? userAdapter.findById(id) : Optional.empty();
        if (!user.isPresent() || user.get().getDeletedAt() != null) {
            throw new DomainException(NOT_FOUND_MSG, User.class.getSimpleName(), NOT_FOUND);
        }
        return user.get();
    }

    @Override
    public User getUserDaoByEmail(String email) {
        return null;
    }

    @Override
    public PageDto<UserDto> getUsers(PageOptions pageOptions) {

        Page<User> users = userAdapter.findAllByDeletedAtIsNull(PageRequest.of(pageOptions.getPage(), pageOptions.getSize()));
        return persistenceMapper.toUsersPageDto(users);
    }

    @Override
    public UserDto getUser(Long id) {

        User user = getUserDao(id);
        return persistenceMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserByEmail(String name) {
        return null;
    }

    @Override
    public UserDto createUser(UserRequest userRequest) {

        User user = persistenceMapper.toUser(userRequest);
        user.setRole(Role.USER);
        User result = userAdapter.save(user);
        return persistenceMapper.toUserDto(result);
    }

    @Override
    public void deleteUser(Long id) {

        User user = getUserDao(id);
        user.setDeletedAt(clock.instant());
        userAdapter.save(user);
    }

    @Override
    public UserDto patchUser(Long id, Map<String, Object> data) {

        User user = getUserDao(id);
        patch(user, data);
        user = userAdapter.save(user);
        return persistenceMapper.toUserDto(user);
    }

    @Override
    public UserDto addCities(Long id, List<Long> citiesId) {

        User user = getUserDao(id);
        List<City> cities = user.getCities();
        citiesId.forEach(i -> {
            City city = cityDaoService.getCityDao(i);
            if (!cities.contains(city)) {
                cities.add(city);
            }
        });
        user.setCities(cities);
        user = userAdapter.save(user);
        return persistenceMapper.toUserDto(user);
    }

    @Override
    public UserDto addDepartments(Long id, List<Long> departmentsId) {

        User user = getUserDao(id);
        List<Department> departments = user.getDepartments();
        departmentsId.forEach(i -> {
            Department department = departmentDaoService.getDepartmentDao(i);
            if (!departments.contains(department)) {
                departments.add(department);
            }
        });
        user.setDepartments(departments);
        user = userAdapter.save(user);
        return persistenceMapper.toUserDto(user);
    }

    @Override
    public UserDto addRegions(Long id, List<Long> regionsId) {

        User user = getUserDao(id);
        List<Region> regions = user.getRegions();
        regionsId.forEach(i -> {
            Region region = regionDaoService.getRegionDao(i);
            if (!regions.contains(region)) {
                regions.add(region);
            }
        });
        user.setRegions(regions);
        user = userAdapter.save(user);
        return persistenceMapper.toUserDto(user);
    }

    @Override
    public UserDto addActivities(Long id, List<Long> activitiesId) {

        User user = getUserDao(id);
        List<Activity> activities = user.getActivities();
        activitiesId.forEach(i -> {
            Activity activity = activityDaoService.getActivityDao(i);
            if (!activities.contains(activity)) {
                activities.add(activity);
            }
        });
        user.setActivities(activities);
        user = userAdapter.save(user);
        return persistenceMapper.toUserDto(user);
    }

    @Override
    public UserDto addEvents(Long id, List<Long> eventsId) {

        User user = getUserDao(id);
        List<Event> events = user.getEvents();
        eventsId.forEach(i -> {
            Event event = eventDaoService.getEventDao(i);
            if (!events.contains(event)) {
                events.add(event);
            }
        });
        user.setEvents(events);
        user = userAdapter.save(user);
        return persistenceMapper.toUserDto(user);
    }

    @Override
    public UserDto removeCities(Long id, List<Long> citiesId) {

        User user = getUserDao(id);
        List<City> cities = user.getCities().stream().filter(c -> !citiesId.contains(c.getId())).collect(toList());
        user.setCities(cities);
        user = userAdapter.save(user);
        return persistenceMapper.toUserDto(user);
    }

    @Override
    public UserDto removeDepartments(Long id, List<Long> departmentsId) {

        User user = getUserDao(id);
        List<Department> departments = user.getDepartments().stream().filter(c -> !departmentsId.contains(c.getId())).collect(toList());
        user.setDepartments(departments);
        user = userAdapter.save(user);
        return persistenceMapper.toUserDto(user);
    }

    @Override
    public UserDto removeRegions(Long id, List<Long> regionsId) {

        User user = getUserDao(id);
        List<Region> regions = user.getRegions().stream().filter(c -> !regionsId.contains(c.getId())).collect(toList());
        user.setRegions(regions);
        user = userAdapter.save(user);
        return persistenceMapper.toUserDto(user);
    }

    @Override
    public UserDto removeActivities(Long id, List<Long> activitiesId) {

        User user = getUserDao(id);
        List<Activity> activities = user.getActivities().stream().filter(c -> !activitiesId.contains(c.getId())).collect(toList());
        user.setActivities(activities);
        user = userAdapter.save(user);
        return persistenceMapper.toUserDto(user);
    }

    @Override
    public UserDto removeEvents(Long id, List<Long> eventsId) {

        User user = getUserDao(id);
        List<Event> events = user.getEvents().stream().filter(c -> !eventsId.contains(c.getId())).collect(toList());
        user.setEvents(events);
        user = userAdapter.save(user);
        return persistenceMapper.toUserDto(user);
    }

    @Override
    protected String[] getIgnoreFieldToPatch() {
        return new String[]{"role"};
    }
}
