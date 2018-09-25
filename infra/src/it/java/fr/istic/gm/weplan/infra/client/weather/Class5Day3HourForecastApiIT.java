package fr.istic.gm.weplan.infra.client.weather;

import fr.istic.gm.weplan.infra.client.weather.api.Class5Day3HourForecastApi;
import fr.istic.gm.weplan.infra.client.weather.api.model.ForecastHourly;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class Class5Day3HourForecastApiIT {

    private Class5Day3HourForecastApi class5Day3HourForecastApi;

    private String apiKey;

    @Before
    public void setUp() throws IOException {
        apiKey = System.getenv("WEATHER_API");
        if (apiKey == null) {
            Properties prop = new Properties();
            InputStream input = new FileInputStream("../server/src/main/resources/application-weather.properties");
            prop.load(input);
            apiKey = prop.getProperty("weplan.weather.api-key");
        }
        class5Day3HourForecastApi = new Class5Day3HourForecastApi();
    }

    @Test
    public void shouldRetrieveWeatherOfTheNext5Days() {

        ForecastHourly forecastHourly = class5Day3HourForecastApi.forecast3hourlyGet(35700, apiKey, "FR", null, null, "fr", null);

        assertThat(forecastHourly, notNullValue());
        assertThat(forecastHourly.getCityName(), equalTo("Ille-et-Vilaine"));
        assertThat(forecastHourly.getData(), hasSize(40));
    }
}
