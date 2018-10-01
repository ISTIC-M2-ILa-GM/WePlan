package fr.istic.gm.weplan.domain.model.mapper;

import fr.istic.gm.weplan.domain.model.dto.ActivityDto;
import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.ActivityType;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.request.ActivityRequest;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.model.request.DepartmentRequest;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.MockitoJUnitRunner;
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
import static fr.istic.gm.weplan.domain.TestData.someRegion;
import static fr.istic.gm.weplan.domain.TestData.someRegionRequest;
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
        assertThat(mapper.toCitiesPageDto(null), nullValue());

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
        assertThat(mapper.toDepartmentsPageDto(null), nullValue());

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
        assertThat(mapper.toRegionsPageDto(null), nullValue());

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
        activityRequest.setActivityType(ActivityType.SAILING.toString());

        Activity activity = mapper.toActivity(activityRequest);
        assertThat(activity, notNullValue());
        assertThat(activity.getName(), equalTo(activityRequest.getName()));
        assertThat(activity.getCost(), equalTo(activityRequest.getCost()));
        assertThat(activity.getActivityType().toString(), equalTo(activityRequest.getActivityType()));
        assertThat(activity.getCities(), equalTo(null));
    }

    @Test
    public void shouldMapAPageActivity() {
        assertThat(mapper.toActivitiesPageDto(null), nullValue());

        Activity activity = someActivity();
        PageImpl<Activity> page = new PageImpl<>(Collections.singletonList(activity), PageRequest.of(1, 2), 10);

        PageDto<ActivityDto> activitiesPageDto = mapper.toActivitiesPageDto(page);

        assertThat(activitiesPageDto, notNullValue());
        assertThat(activitiesPageDto.getSize(), equalTo(2));
        assertThat(activitiesPageDto.getTotalPages(), equalTo(5));
        assertThat(activitiesPageDto.getResults(), hasSize(1));
        assertThat(activitiesPageDto.getResults().get(0).getId(), equalTo(activity.getId()));
    }
}
