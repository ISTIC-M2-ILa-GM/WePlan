package fr.istic.gm.weplan.domain.model.mapper;

import fr.istic.gm.weplan.domain.model.dto.ActivityDto;
import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.entities.Event;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.entities.User;
import fr.istic.gm.weplan.domain.model.request.ActivityRequest;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.model.request.DepartmentRequest;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;
import fr.istic.gm.weplan.domain.model.request.UserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fr.istic.gm.weplan.domain.TestData.someActivity;
import static fr.istic.gm.weplan.domain.TestData.someActivityRequest;
import static fr.istic.gm.weplan.domain.TestData.someCity;
import static fr.istic.gm.weplan.domain.TestData.someCityRequest;
import static fr.istic.gm.weplan.domain.TestData.someDepartment;
import static fr.istic.gm.weplan.domain.TestData.someDepartmentRequest;
import static fr.istic.gm.weplan.domain.TestData.someEvent;
import static fr.istic.gm.weplan.domain.TestData.someRegion;
import static fr.istic.gm.weplan.domain.TestData.someRegionRequest;
import static fr.istic.gm.weplan.domain.TestData.someUser;
import static fr.istic.gm.weplan.domain.TestData.someUserRequest;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(MockitoJUnitRunner.class)
public class PersistenceMapperTest {

    private PersistenceMapper mapper;

    @Before
    public void setUp() {
        mapper = Mappers.getMapper(PersistenceMapper.class);
    }

    @Test
    public void shouldMapCity() {

        assertThat(mapper.toCityDto(null), nullValue());

        City city = someCity();
        CityDto cityDto = mapper.toCityDto(city);
        assertThat(cityDto, notNullValue());
        assertThat(cityDto.getName(), equalTo(city.getName()));
        assertThat(cityDto.getPostalCode(), equalTo(city.getPostalCode()));
        assertThat(cityDto.getCreatedAt(), equalTo(city.getCreatedAt()));
        assertThat(cityDto.getDeletedAt(), equalTo(city.getDeletedAt()));
        assertThat(cityDto.getId(), equalTo(city.getId()));
        assertThat(cityDto.getUpdatedAt(), equalTo(city.getUpdatedAt()));
        assertThat(cityDto.getDepartment(), notNullValue());
    }

    @Test
    public void shouldMapCities() {

        assertThat(mapper.toCitiesDto(null), nullValue());
        assertThat(mapper.toCitiesDto(new ArrayList<>()), hasSize(0));
        assertThat(mapper.toCitiesDto(Collections.singletonList(someCity())), hasSize(1));

        List<City> cities = new ArrayList<>();
        cities.add(someCity());
        cities.add(someCity());
        assertThat(mapper.toCitiesDto(cities), hasSize(2));
    }

    @Test
    public void shouldMapCityRequest() {

        assertThat(mapper.toCity(null), nullValue());

        CityRequest cityRequest = someCityRequest();
        City city = mapper.toCity(cityRequest);
        assertThat(city, notNullValue());
        assertThat(city.getName(), equalTo(cityRequest.getName()));
        assertThat(city.getPostalCode(), equalTo(cityRequest.getPostalCode()));
        assertThat(city.getDepartment(), nullValue());
    }

    @Test
    public void shouldMapAPageCity() {
        assertThat(mapper.toCitiesPageDto((Page<City>) null), nullValue());

        City city = someCity();
        PageImpl<City> page = new PageImpl<>(Collections.singletonList(city), PageRequest.of(1, 2), 10);

        PageDto<CityDto> citiesPageDto = mapper.toCitiesPageDto(page);

        assertThat(citiesPageDto, notNullValue());
        assertThat(citiesPageDto.getSize(), equalTo(2));
        assertThat(citiesPageDto.getTotalPages(), equalTo(5));
        assertThat(citiesPageDto.getResults(), hasSize(1));
        assertThat(citiesPageDto.getResults().get(0).getId(), equalTo(city.getId()));
    }

    @Test
    public void shouldMapADepartment() {

        assertThat(mapper.toDepartmentDto(null), nullValue());

        Department department = someDepartment();
        DepartmentDto departmentDto = mapper.toDepartmentDto(department);
        assertThat(departmentDto, notNullValue());
        assertThat(departmentDto.getName(), equalTo(department.getName()));
        assertThat(departmentDto.getCode(), equalTo(department.getCode()));
        assertThat(departmentDto.getRegion(), notNullValue());
    }

    @Test
    public void shouldMapDepartments() {

        assertThat(mapper.toDepartmentsDto(null), nullValue());
        assertThat(mapper.toDepartmentsDto(new ArrayList<>()), hasSize(0));
        assertThat(mapper.toDepartmentsDto(Collections.singletonList(someDepartment())), hasSize(1));

        List<Department> departments = new ArrayList<>();
        departments.add(someDepartment());
        departments.add(someDepartment());
        assertThat(mapper.toDepartmentsDto(departments), hasSize(2));
    }

    @Test
    public void shouldMapDepartmentRequest() {

        assertThat(mapper.toDepartment(null), nullValue());

        DepartmentRequest departmentRequest = someDepartmentRequest();
        Department department = mapper.toDepartment(departmentRequest);
        assertThat(department, notNullValue());
        assertThat(department.getName(), equalTo(departmentRequest.getName()));
        assertThat(department.getCode(), equalTo(departmentRequest.getCode()));
        assertThat(department.getRegion(), nullValue());
    }

    @Test
    public void shouldMapAPageDepartment() {
        assertThat(mapper.toDepartmentsPageDto((Page<Department>) null), nullValue());

        Department department = someDepartment();
        PageImpl<Department> page = new PageImpl<>(Collections.singletonList(department), PageRequest.of(1, 2), 10);

        PageDto<DepartmentDto> departmentDtoPageDto = mapper.toDepartmentsPageDto(page);

        assertThat(departmentDtoPageDto, notNullValue());
        assertThat(departmentDtoPageDto.getSize(), equalTo(2));
        assertThat(departmentDtoPageDto.getTotalPages(), equalTo(5));
        assertThat(departmentDtoPageDto.getResults(), hasSize(1));
        assertThat(departmentDtoPageDto.getResults().get(0).getId(), equalTo(department.getId()));
    }

    @Test
    public void shouldMapRegion() {

        assertThat(mapper.toRegionDto(null), nullValue());

        Region region = someRegion();
        RegionDto regionDto = mapper.toRegionDto(region);
        assertThat(regionDto, notNullValue());
        assertThat(regionDto.getId(), equalTo(region.getId()));
        assertThat(regionDto.getName(), equalTo(region.getName()));
        assertThat(regionDto.getCreatedAt(), equalTo(region.getCreatedAt()));
        assertThat(regionDto.getDeletedAt(), equalTo(region.getDeletedAt()));
        assertThat(regionDto.getUpdatedAt(), equalTo(region.getUpdatedAt()));
    }

    @Test
    public void shouldMapRegions() {

        assertThat(mapper.toRegionsDto(null), nullValue());
        assertThat(mapper.toRegionsDto(new ArrayList<>()), hasSize(0));
        assertThat(mapper.toRegionsDto(Collections.singletonList(someRegion())), hasSize(1));

        List<Region> regions = new ArrayList<>();
        regions.add(someRegion());
        regions.add(someRegion());
        assertThat(mapper.toRegionsDto(regions), hasSize(2));
    }

    @Test
    public void shouldMapRegionRequest() {

        assertThat(mapper.toRegion(null), nullValue());

        RegionRequest regionRequest = someRegionRequest();
        Region region = mapper.toRegion(regionRequest);
        assertThat(region, notNullValue());
        assertThat(region.getName(), equalTo(regionRequest.getName()));
    }

    @Test
    public void shouldMapAPageRegion() {
        assertThat(mapper.toRegionsPageDto((Page<Region>) null), nullValue());

        Region region = someRegion();
        PageImpl<Region> page = new PageImpl<>(Collections.singletonList(region), PageRequest.of(1, 2), 10);

        PageDto<RegionDto> regionsPageDto = mapper.toRegionsPageDto(page);

        assertThat(regionsPageDto, notNullValue());
        assertThat(regionsPageDto.getSize(), equalTo(2));
        assertThat(regionsPageDto.getTotalPages(), equalTo(5));
        assertThat(regionsPageDto.getResults(), hasSize(1));
        assertThat(regionsPageDto.getResults().get(0).getId(), equalTo(region.getId()));
    }


    @Test
    public void shouldMapActivity() {

        assertThat(mapper.toRegionDto(null), nullValue());

        Activity activity = someActivity();
        ActivityDto activityDto = mapper.toActivityDto(activity);
        assertThat(activityDto, notNullValue());
        assertThat(activityDto.getId(), equalTo(activity.getId()));
        assertThat(activityDto.getName(), equalTo(activity.getName()));
        assertThat(activityDto.getCreatedAt(), equalTo(activity.getCreatedAt()));
        assertThat(activityDto.getDeletedAt(), equalTo(activity.getDeletedAt()));
        assertThat(activityDto.getUpdatedAt(), equalTo(activity.getUpdatedAt()));
    }

    @Test
    public void shouldMapActivities() {

        assertThat(mapper.toActivitiesDto(null), nullValue());
        assertThat(mapper.toActivitiesDto(new ArrayList<>()), hasSize(0));
        assertThat(mapper.toActivitiesDto(Collections.singletonList(someActivity())), hasSize(1));

        List<Activity> activities = new ArrayList<>();
        activities.add(someActivity());
        activities.add(someActivity());
        assertThat(mapper.toActivitiesDto(activities), hasSize(2));
    }

    @Test
    public void shouldMapActivityRequest() {

        assertThat(mapper.toActivity(null), nullValue());

        ActivityRequest activityRequest = someActivityRequest();

        Activity activity = mapper.toActivity(activityRequest);
        assertThat(activity, notNullValue());
        assertThat(activity.getName(), equalTo(activityRequest.getName()));
        assertThat(activity.getCost(), equalTo(activityRequest.getCost()));
        assertThat(activity.getActivityType().toString(), equalTo(activityRequest.getActivityType()));
        assertThat(activity.getCities(), equalTo(null));
    }

    @Test
    public void shouldMapAPageActivity() {
        assertThat(mapper.toActivitiesPageDto((Page<Activity>) null), nullValue());

        Activity activity = someActivity();
        PageImpl<Activity> page = new PageImpl<>(Collections.singletonList(activity), PageRequest.of(1, 2), 10);

        PageDto<ActivityDto> activitiesPageDto = mapper.toActivitiesPageDto(page);

        assertThat(activitiesPageDto, notNullValue());
        assertThat(activitiesPageDto.getSize(), equalTo(2));
        assertThat(activitiesPageDto.getTotalPages(), equalTo(5));
        assertThat(activitiesPageDto.getResults(), hasSize(1));
        assertThat(activitiesPageDto.getResults().get(0).getId(), equalTo(activity.getId()));
    }

    @Test
    public void shouldMapAnUser() {

        assertThat(mapper.toUserDto(null), nullValue());

        User user = someUser();
        UserDto userDto = mapper.toUserDto(user);
        assertThat(userDto, notNullValue());
        assertThat(userDto.getFirstName(), equalTo(user.getFirstName()));
        assertThat(userDto.getLastName(), equalTo(user.getLastName()));
        assertThat(userDto.getRole().name(), equalTo(user.getRole().name()));
        assertThat(userDto.getEmail(), equalTo(user.getEmail()));
        assertThat(userDto.getId(), equalTo(user.getId()));
        assertThat(userDto.getCreatedAt(), equalTo(user.getCreatedAt()));
        assertThat(userDto.getDeletedAt(), equalTo(user.getDeletedAt()));
        assertThat(userDto.getUpdatedAt(), equalTo(user.getUpdatedAt()));
        assertThat(userDto.getCities(), notNullValue());
        assertThat(userDto.getCities(), hasSize(user.getCities().size()));
        assertThat(userDto.getDepartments(), notNullValue());
        assertThat(userDto.getDepartments(), hasSize(user.getDepartments().size()));
        assertThat(userDto.getEvents(), notNullValue());
        assertThat(userDto.getEvents(), hasSize(user.getEvents().size()));
        assertThat(userDto.getRegions(), notNullValue());
        assertThat(userDto.getRegions(), hasSize(user.getRegions().size()));
    }

    @Test
    public void shouldMapUsers() {

        assertThat(mapper.toUsersDto(null), nullValue());
        assertThat(mapper.toUsersDto(new ArrayList<>()), hasSize(0));
        assertThat(mapper.toUsersDto(Collections.singletonList(someUser())), hasSize(1));

        List<User> users = new ArrayList<>();
        users.add(someUser());
        users.add(someUser());
        assertThat(mapper.toUsersDto(users), hasSize(2));
    }

    @Test
    public void shouldMapUserRequest() {

        assertThat(mapper.toUser(null), nullValue());

        UserRequest userRequest = someUserRequest();
        User user = mapper.toUser(userRequest);
        assertThat(user, notNullValue());
        assertThat(user.getFirstName(), equalTo(userRequest.getFirstName()));
        assertThat(user.getLastName(), equalTo(userRequest.getLastName()));
        assertThat(user.getEmail(), equalTo(userRequest.getEmail()));
    }

    @Test
    public void shouldMapAPageUser() {
        assertThat(mapper.toUsersPageDto(null), nullValue());

        User user = someUser();
        PageImpl<User> page = new PageImpl<>(Collections.singletonList(user), PageRequest.of(1, 2), 10);

        PageDto<UserDto> userDtoPageDto = mapper.toUsersPageDto(page);

        assertThat(userDtoPageDto, notNullValue());
        assertThat(userDtoPageDto.getSize(), equalTo(2));
        assertThat(userDtoPageDto.getTotalPages(), equalTo(5));
        assertThat(userDtoPageDto.getResults(), hasSize(1));
        assertThat(userDtoPageDto.getResults().get(0).getId(), equalTo(user.getId()));
    }

    @Test
    public void shouldMapEvent() {

        assertThat(mapper.toEventDto(null), nullValue());

        Event event = someEvent();
        EventDto eventDto = mapper.toEventDto(event);
        assertThat(eventDto, notNullValue());
        assertThat(eventDto.getActivity(), notNullValue());
        assertThat(eventDto.getCity(), notNullValue());
    }

    @Test
    public void shouldMapEvents() {

        assertThat(mapper.toEventsDto(null), nullValue());
        assertThat(mapper.toEventsDto(new ArrayList<>()), hasSize(0));
        assertThat(mapper.toEventsDto(Collections.singletonList(someEvent())), hasSize(1));

        List<Event> events = new ArrayList<>();
        events.add(someEvent());
        events.add(someEvent());
        assertThat(mapper.toEventsDto(events), hasSize(2));
    }

    @Test
    public void shouldMapAPageEvent() {
        assertThat(mapper.toEventsPageDto(null), nullValue());

        Event event = someEvent();
        PageImpl<Event> page = new PageImpl<>(Collections.singletonList(event), PageRequest.of(1, 2), 10);

        PageDto<EventDto> eventDtoPageDto = mapper.toEventsPageDto(page);

        assertThat(eventDtoPageDto, notNullValue());
        assertThat(eventDtoPageDto.getSize(), equalTo(2));
        assertThat(eventDtoPageDto.getTotalPages(), equalTo(5));
        assertThat(eventDtoPageDto.getResults(), hasSize(1));
        assertThat(eventDtoPageDto.getResults().get(0).getId(), equalTo(event.getId()));
    }

    @Test
    public void shouldMapAListCity() {
        assertThat(mapper.toCitiesPageDto((List<City>) null), nullValue());

        City city = someCity();

        PageDto<CityDto> citiesPageDto = mapper.toCitiesPageDto(Collections.singletonList(city));

        assertThat(citiesPageDto, notNullValue());
        assertThat(citiesPageDto.getSize(), equalTo(1));
        assertThat(citiesPageDto.getTotalPages(), equalTo(1));
        assertThat(citiesPageDto.getResults(), hasSize(1));
        assertThat(citiesPageDto.getResults().get(0).getId(), equalTo(city.getId()));
    }

    @Test
    public void shouldMapAListDepartment() {
        assertThat(mapper.toDepartmentsPageDto((List<Department>) null), nullValue());

        Department department = someDepartment();

        PageDto<DepartmentDto> departmentDtoPageDto = mapper.toDepartmentsPageDto(Collections.singletonList(department));

        assertThat(departmentDtoPageDto, notNullValue());
        assertThat(departmentDtoPageDto.getSize(), equalTo(1));
        assertThat(departmentDtoPageDto.getTotalPages(), equalTo(1));
        assertThat(departmentDtoPageDto.getResults(), hasSize(1));
        assertThat(departmentDtoPageDto.getResults().get(0).getId(), equalTo(department.getId()));
    }

    @Test
    public void shouldMapAListRegion() {
        assertThat(mapper.toRegionsPageDto((List<Region>) null), nullValue());

        Region region = someRegion();

        PageDto<RegionDto> regionsPageDto = mapper.toRegionsPageDto(Collections.singletonList(region));

        assertThat(regionsPageDto, notNullValue());
        assertThat(regionsPageDto.getSize(), equalTo(1));
        assertThat(regionsPageDto.getTotalPages(), equalTo(1));
        assertThat(regionsPageDto.getResults(), hasSize(1));
        assertThat(regionsPageDto.getResults().get(0).getId(), equalTo(region.getId()));
    }

    @Test
    public void shouldMapAListActivity() {
        assertThat(mapper.toActivitiesPageDto((List<Activity>) null), nullValue());

        Activity activity = someActivity();

        PageDto<ActivityDto> activitiesPageDto = mapper.toActivitiesPageDto(Collections.singletonList(activity));

        assertThat(activitiesPageDto, notNullValue());
        assertThat(activitiesPageDto.getSize(), equalTo(1));
        assertThat(activitiesPageDto.getTotalPages(), equalTo(1));
        assertThat(activitiesPageDto.getResults(), hasSize(1));
        assertThat(activitiesPageDto.getResults().get(0).getId(), equalTo(activity.getId()));
    }
}
