package fr.istic.gm.weplan.server;

import fr.istic.gm.weplan.domain.model.dto.ActivityDto;
import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.dto.UserDto;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.entities.User;
import fr.istic.gm.weplan.domain.model.request.ActivityRequest;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.model.request.DepartmentRequest;
import fr.istic.gm.weplan.domain.model.request.UserRequest;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Collections;

public class TestData {

    public static final Long ID = 10L;
    public static final String SOME_STRING = "some-string";
    public static final String EMAIL = "an-email@true.com";

    private static final PodamFactory FACTORY = new PodamFactoryImpl();
    private static final String SOME_EMAIL = "an-email@email.fr";

    public static EventDto someEvent() {
        return FACTORY.manufacturePojoWithFullData(EventDto.class);
    }

    public static Department someDepartmentDao() {
        Department department = FACTORY.manufacturePojoWithFullData(Department.class);
        department.setDeletedAt(null);
        return department;
    }

    public static DepartmentRequest someDepartmentRequest() {
        return FACTORY.manufacturePojoWithFullData(DepartmentRequest.class);
    }

    public static DepartmentDto someDepartment() {
        return FACTORY.manufacturePojoWithFullData(DepartmentDto.class);
    }

    public static PageDto<DepartmentDto> somePageDepartments() {
        PageDto<DepartmentDto> departmentsDto = new PageDto<>();
        departmentsDto.setSize(10);
        departmentsDto.setTotalPages(10);
        departmentsDto.setResults(Collections.singletonList(someDepartment()));
        return departmentsDto;
    }

    public static City someCityDao() {
        return FACTORY.manufacturePojoWithFullData(City.class);
    }

    public static CityRequest someCityRequest() {
        CityRequest cityRequest = FACTORY.manufacturePojoWithFullData(CityRequest.class);
        cityRequest.setName(SOME_STRING);
        return cityRequest;
    }

    public static PageRequest somePageOptions() {
        return FACTORY.manufacturePojoWithFullData(PageRequest.class);
    }

    public static PageDto<EventDto> somePageEvents() {
        PageDto<EventDto> eventsDto = new PageDto<>();
        eventsDto.setSize(10);
        eventsDto.setTotalPages(10);
        eventsDto.setResults(Collections.singletonList(someEvent()));
        return eventsDto;
    }

    public static PageRequest firstPageOptions() {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setSize(10);
        pageRequest.setPage(0);
        return pageRequest;
    }

    public static PageDto<CityDto> somePageCities() {
        PageDto<CityDto> citiesDto = new PageDto<>();
        citiesDto.setSize(10);
        citiesDto.setTotalPages(10);
        citiesDto.setResults(Collections.singletonList(someCity()));
        return citiesDto;
    }

    public static CityDto someCity() {
        return FACTORY.manufacturePojoWithFullData(CityDto.class);
    }

    public static PageDto<UserDto> somePageUsers() {
        PageDto<UserDto> citiesDto = new PageDto<>();
        citiesDto.setSize(10);
        citiesDto.setTotalPages(10);
        citiesDto.setResults(Collections.singletonList(someUser()));
        return citiesDto;
    }

    public static UserDto someUser() {
        return FACTORY.manufacturePojoWithFullData(UserDto.class);
    }

    public static UserRequest someUserRequest() {
        UserRequest userRequest = FACTORY.manufacturePojoWithFullData(UserRequest.class);
        userRequest.setEmail(SOME_EMAIL);
        userRequest.setFirstName(SOME_STRING);
        userRequest.setLastName(SOME_STRING);
        userRequest.setPassword(SOME_STRING);
        return userRequest;
    }

    public static User someUserDao() {
        User user = FACTORY.manufacturePojoWithFullData(User.class);
        user.setRegions(null);
        user.setEvents(null);
        user.setDepartments(null);
        user.setCities(null);
        user.setActivities(null);
        user.setId(null);
        user.setCreatedAt(null);
        user.setDeletedAt(null);
        user.setUpdatedAt(null);
        return user;
    }

    public static PageDto<RegionDto> somePageRegions() {
        PageDto<RegionDto> regionsDto = new PageDto<>();
        regionsDto.setSize(10);
        regionsDto.setTotalPages(10);
        regionsDto.setResults(Collections.singletonList(someRegion()));
        return regionsDto;
    }

    public static RegionDto someRegion() {
        return FACTORY.manufacturePojoWithFullData(RegionDto.class);
    }

    public static Region someRegionDao() {
        return FACTORY.manufacturePojoWithFullData(Region.class);
    }

    public static ActivityRequest someActivityRequest() {
        return FACTORY.manufacturePojoWithFullData(ActivityRequest.class);
    }

    public static ActivityDto someActivity() {
        return FACTORY.manufacturePojoWithFullData(ActivityDto.class);
    }

    public static PageDto<ActivityDto> somePageActivities() {
        PageDto<ActivityDto> activitiesDto = new PageDto<>();
        activitiesDto.setSize(10);
        activitiesDto.setTotalPages(10);
        activitiesDto.setResults(Collections.singletonList(someActivity()));
        return activitiesDto;
    }
}
