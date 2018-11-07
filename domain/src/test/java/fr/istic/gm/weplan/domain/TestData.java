package fr.istic.gm.weplan.domain;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.entities.*;
import fr.istic.gm.weplan.domain.model.request.*;
import fr.istic.gm.weplan.domain.model.weather.Weather;
import fr.istic.gm.weplan.domain.model.weather.Week;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;

public class TestData {

    public static final Long ID = 10L;
    public static final String EMAIL = "an@email.com";
    public static final String SOME_STRING = "some-string";

    private static final PodamFactory FACTORY = new PodamFactoryImpl();

    public static Department someDepartment() {
        Department department = FACTORY.manufacturePojoWithFullData(Department.class);
        department.setDeletedAt(null);
        return department;
    }

    public static DepartmentRequest someDepartmentRequest() {
        return FACTORY.manufacturePojoWithFullData(DepartmentRequest.class);
    }

    public static Region someRegion() {
        Region region = FACTORY.manufacturePojoWithFullData(Region.class);
        region.setDeletedAt(null);
        return region;
    }

    public static User someUser() {
        User user = FACTORY.manufacturePojoWithFullData(User.class);
        user.setDeletedAt(null);
        return user;
    }

    public static Event someEvent() {
        Event event = FACTORY.manufacturePojoWithFullData(Event.class);
        event.setDeletedAt(null);
        return event;
    }

    public static UserRequest someUserRequest() {
        return FACTORY.manufacturePojoWithFullData(UserRequest.class);
    }

    public static Activity someActivity() {
        Activity activity = FACTORY.manufacturePojoWithFullData(Activity.class);
        activity.setDeletedAt(null);
        return activity;
    }

    public static ActivityRequest someActivityRequest() {
        ActivityRequest activityRequest = FACTORY.manufacturePojoWithFullData(ActivityRequest.class);
        activityRequest.setActivityType(ActivityType.SAILING.toString());
        return activityRequest;
    }

    public static CityRequest someCityRequest() {
        return FACTORY.manufacturePojoWithFullData(CityRequest.class);
    }

    public static PageRequest somePageOptions() {
        return FACTORY.manufacturePojoWithFullData(PageRequest.class);
    }

    public static PageDto<CityDto> somePageDtoCities() {
        PageDto<CityDto> citiesDto = new PageDto<>();
        citiesDto.setSize(10);
        citiesDto.setTotalPages(10);
        citiesDto.setResults(Collections.singletonList(someCityDto()));
        return citiesDto;
    }

    public static CityDto someCityDto() {
        return FACTORY.manufacturePojoWithFullData(CityDto.class);
    }

    public static RegionDto someRegionDto() {
        return FACTORY.manufacturePojoWithFullData(RegionDto.class);
    }

    public static DepartmentDto someDepartmentDto() {
        return FACTORY.manufacturePojoWithFullData(DepartmentDto.class);
    }

    public static City someCity() {
        City city = FACTORY.manufacturePojoWithFullData(City.class);
        city.setDeletedAt(null);
        return city;
    }

    public static RegionRequest someRegionRequest() {
        return FACTORY.manufacturePojoWithFullData(RegionRequest.class);
    }

    public static Weather someWeather() {
        return FACTORY.manufacturePojoWithFullData(Weather.class);
    }

    public static Week someWeek() {
        Instant someSaturday = LocalDateTime.of(2018, 11, 3, 12, 0).toInstant(ZoneOffset.UTC);

        Weather weather1 = someWeather();
        weather1.setCode("800");
        weather1.setDate(someSaturday);

        Instant someFriday = LocalDateTime.of(2018, 11, 2, 12, 0).toInstant(ZoneOffset.UTC);

        Weather weather2 = someWeather();
        weather2.setCode("800");
        weather2.setDate(someFriday);

        Instant someSunday = LocalDateTime.of(2018, 11, 4, 12, 0).toInstant(ZoneOffset.UTC);

        Weather weather3 = someWeather();
        weather3.setCode("500");
        weather3.setDate(someSunday);

        Week week = new Week();
        week.setWeathers(Arrays.asList(weather1, weather2, weather3));

        return week;
    }
}
