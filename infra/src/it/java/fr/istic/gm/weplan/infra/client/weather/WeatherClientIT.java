package fr.istic.gm.weplan.infra.client.weather;

import fr.istic.gm.weplan.domain.model.weather.Week;
import fr.istic.gm.weplan.infra.client.weather.config.properties.WeatherProperties;
import fr.istic.gm.weplan.infra.client.weather.generated.api.Class5Day3HourForecastApi;
import fr.istic.gm.weplan.infra.client.weather.impl.WeatherClientImpl;
import fr.istic.gm.weplan.infra.client.weather.mapper.WeatherMapper;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.io.IOException;

import static fr.istic.gm.weplan.infra.TestData.getWeatherApiKey;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

public class WeatherClientIT {

    private WeatherClient weatherClient;

    private WeatherMapper mapper;

    private WeatherProperties fakeWeatherProperties;

    private Class5Day3HourForecastApi class5Day3HourForecastApi;

    @Before
    public void setUp() throws IOException {
        mapper = Mappers.getMapper(WeatherMapper.class);
        fakeWeatherProperties = new WeatherProperties();
        fakeWeatherProperties.setApiKey(getWeatherApiKey());
        class5Day3HourForecastApi = new Class5Day3HourForecastApi();
        weatherClient = new WeatherClientImpl(class5Day3HourForecastApi, fakeWeatherProperties, mapper);
    }

    @Test
    public void shouldRetrieveWeather() {

        Week weatherWeek = weatherClient.getWeatherWeek(35700);

        assertThat(weatherWeek, notNullValue());
        assertThat(weatherWeek.getWeathers(), notNullValue());
        assertThat(weatherWeek.getWeathers(), hasSize(40));
        assertThat(weatherWeek.getWeathers().get(0).getIcon(), notNullValue());
        assertThat(weatherWeek.getWeathers().get(0).getDescription(), notNullValue());
        assertThat(weatherWeek.getWeathers().get(0).getCode(), notNullValue());
    }
}
