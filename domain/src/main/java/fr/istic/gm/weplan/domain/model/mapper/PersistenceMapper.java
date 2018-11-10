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
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersistenceMapper {

    RegionDto toRegionDto(Region region);

    List<RegionDto> toRegionsDto(List<Region> regions);

    default PageDto<RegionDto> toRegionsPageDto(Page<Region> regions) {
        if (regions == null)
            return null;

        PageDto<RegionDto> regionsDto = new PageDto<>();
        regionsDto.setSize(regions.getSize());
        regionsDto.setTotalPages(regions.getTotalPages());
        regionsDto.setResults(toRegionsDto(regions.getContent()));
        return regionsDto;
    }

    Region toRegion(RegionRequest regionDto);

    CityDto toCityDto(City city);

    List<CityDto> toCitiesDto(List<City> cities);

    City toCity(CityRequest cityRequest);

    default PageDto<CityDto> toCitiesPageDto(Page<City> cities) {
        if (cities == null) {
            return null;
        }
        PageDto<CityDto> citiesDto = new PageDto<>();
        citiesDto.setSize(cities.getSize());
        citiesDto.setTotalPages(cities.getTotalPages());
        citiesDto.setResults(toCitiesDto(cities.getContent()));
        return citiesDto;
    }

    DepartmentDto toDepartmentDto(Department department);

    Department toDepartment(DepartmentRequest departmentRequest);

    List<DepartmentDto> toDepartmentsDto(List<Department> departments);

    default PageDto<DepartmentDto> toDepartmentsPageDto(Page<Department> departments) {
        if (departments == null) {
            return null;
        }
        PageDto<DepartmentDto> departmentsDto = new PageDto<>();
        departmentsDto.setSize(departments.getSize());
        departmentsDto.setTotalPages(departments.getTotalPages());
        departmentsDto.setResults(toDepartmentsDto(departments.getContent()));
        return departmentsDto;
    }

    Activity toActivity(ActivityRequest activityRequest);

    ActivityDto toActivityDto(Activity activity);

    List<ActivityDto> toActivitiesDto(List<Activity> activities);

    default PageDto<ActivityDto> toActivitiesPageDto(Page<Activity> activities) {
        if (activities == null)
            return null;

        PageDto<ActivityDto> activitiesDto = new PageDto<>();
        activitiesDto.setSize(activities.getSize());
        activitiesDto.setTotalPages(activities.getTotalPages());
        activitiesDto.setResults(toActivitiesDto(activities.getContent()));
        return activitiesDto;
    }

    default PageDto<UserDto> toUsersPageDto(Page<User> users) {
        if (users == null) {
            return null;
        }
        PageDto<UserDto> userDtoPageDto = new PageDto<>();
        userDtoPageDto.setSize(users.getSize());
        userDtoPageDto.setTotalPages(users.getTotalPages());
        userDtoPageDto.setResults(toUsersDto(users.getContent()));
        return userDtoPageDto;
    }

    UserDto toUserDto(User user);

    User toUser(UserRequest userRequest);

    List<UserDto> toUsersDto(List<User> content);

    EventDto toEventDto(Event event);

    List<EventDto> toEventsDto(List<Event> events);

    default PageDto<EventDto> toEventsPageDto(Page<Event> events) {
        if (events == null) {
            return null;
        }
        PageDto<EventDto> eventsDto = new PageDto<>();
        eventsDto.setSize(events.getSize());
        eventsDto.setTotalPages(events.getTotalPages());
        eventsDto.setResults(toEventsDto(events.getContent()));
        return eventsDto;
    }

    default PageDto<CityDto> toCitiesPageDto(List<City> cities) {
        if (cities == null) {
            return null;
        }
        PageDto<CityDto> pageDto = new PageDto<>();
        pageDto.setResults(toCitiesDto(cities));
        pageDto.setSize(cities.size());
        pageDto.setTotalPages(1);
        return pageDto;
    }

    default PageDto<DepartmentDto> toDepartmentsPageDto(List<Department> departments) {
        if (departments == null) {
            return null;
        }
        PageDto<DepartmentDto> pageDto = new PageDto<>();
        pageDto.setResults(toDepartmentsDto(departments));
        pageDto.setSize(departments.size());
        pageDto.setTotalPages(1);
        return pageDto;
    }

    default PageDto<ActivityDto> toActivitiesPageDto(List<Activity> activities) {
        if (activities == null) {
            return null;
        }
        PageDto<ActivityDto> pageDto = new PageDto<>();
        pageDto.setResults(toActivitiesDto(activities));
        pageDto.setSize(activities.size());
        pageDto.setTotalPages(1);
        return pageDto;
    }

    default PageDto<RegionDto> toRegionsPageDto(List<Region> regions) {
        if (regions == null) {
            return null;
        }
        PageDto<RegionDto> pageDto = new PageDto<>();
        pageDto.setResults(toRegionsDto(regions));
        pageDto.setSize(regions.size());
        pageDto.setTotalPages(1);
        return pageDto;
    }
}
