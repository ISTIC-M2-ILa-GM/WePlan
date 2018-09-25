package fr.istic.gm.weplan.infra.client.weather;

import io.swagger.client.api.Class5Day3HourForecastApi;
import io.swagger.client.model.ForecastHourly;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class Class5Day3HourForecastApiIT {

    private Class5Day3HourForecastApi class5Day3HourForecastApi;
    private String apiKey;

    @Before
    public void setUp() {
        apiKey = System.getenv("WEATHER_API");
        class5Day3HourForecastApi = new Class5Day3HourForecastApi();
    }

    @Test
    public void shouldRetrieveWeatherOfTheNext5Days() {
        ForecastHourly forecastHourly = class5Day3HourForecastApi.forecast3hourlypostalCodepostalCodeGet(35700, apiKey, "FR", new BigDecimal(5), "M", "fr", "");
    }
}
