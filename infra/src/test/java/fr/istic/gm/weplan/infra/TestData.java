package fr.istic.gm.weplan.infra;

import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.entities.Event;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.entities.User;
import fr.istic.gm.weplan.infra.client.weather.generated.api.model.ForecastHour;
import fr.istic.gm.weplan.infra.client.weather.generated.api.model.ForecastHourly;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

public class TestData {

    private static final PodamFactory FACTORY = new PodamFactoryImpl();
    public static final int POSTAL_CODE = 35000;
    public static final String API_KEY = "an-api-key";
    public static final String SOME_STRING = "some-string";

    public static ForecastHourly someForecastHourly() {
        ForecastHourly forecastHourly = FACTORY.manufacturePojo(ForecastHourly.class);
        forecastHourly.getData().forEach(d -> d.datetime("2018-10-04:03"));
        return forecastHourly;
    }

    public static ForecastHour someForecastHour() {
        ForecastHour forecastHour = FACTORY.manufacturePojo(ForecastHour.class);
        forecastHour.setDatetime("2018-10-04:21");
        return forecastHour;
    }

    public static EventDto someEventDto() {
        return FACTORY.manufacturePojoWithFullData(EventDto.class);
    }

    public static Event someEvent() {
        Event event = FACTORY.manufacturePojoWithFullData(Event.class);
        event.setDeletedAt(null);
        event.setDate(Instant.now().plus(1, ChronoUnit.DAYS));
        return event;
    }

    public static Department someDepartment() {
        Department department = FACTORY.manufacturePojoWithFullData(Department.class);
        department.setDeletedAt(null);
        return department;
    }

    public static City someCity() {
        City city = FACTORY.manufacturePojoWithFullData(City.class);
        city.setDeletedAt(null);
        return city;
    }

    public static Activity someActivity() {
        Activity activity = FACTORY.manufacturePojoWithFullData(Activity.class);
        activity.setDeletedAt(null);
        return activity;
    }

    public static String getWeatherApiKey() throws IOException {
        String apiKey = System.getenv("WEATHER_API");
        if (apiKey == null) {
            Properties prop = new Properties();
            InputStream input = new FileInputStream("../server/src/main/resources/application-weather.properties");
            prop.load(input);
            apiKey = prop.getProperty("weplan.weather.api-key");
        }
        return apiKey;
    }

    public static User someUser() {
        User user = FACTORY.manufacturePojoWithFullData(User.class);
        user.setId(null);
        user.setCreatedAt(null);
        user.setUpdatedAt(null);
        user.setDeletedAt(null);
        user.setCities(null);
        user.setDepartments(null);
        user.setEvents(null);
        user.setRegions(null);
        user.setActivities(null);
        return user;
    }

    public static Region someRegion() {
        Region region = FACTORY.manufacturePojoWithFullData(Region.class);
        region.setId(null);
        region.setCreatedAt(null);
        region.setUpdatedAt(null);
        region.setDeletedAt(null);
        return region;
    }

}
