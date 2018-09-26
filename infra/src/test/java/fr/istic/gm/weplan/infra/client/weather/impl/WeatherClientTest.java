package fr.istic.gm.weplan.infra.client.weather.impl;

import fr.istic.gm.weplan.domain.model.weather.Week;
import fr.istic.gm.weplan.infra.client.weather.WeatherClient;
import fr.istic.gm.weplan.infra.client.weather.api.Class5Day3HourForecastApi;
import fr.istic.gm.weplan.infra.client.weather.api.model.ForecastHourly;
import fr.istic.gm.weplan.infra.client.weather.config.properties.WeatherProperties;
import fr.istic.gm.weplan.infra.client.weather.mapper.WeatherMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static fr.istic.gm.weplan.infra.TestData.API_KEY;
import static fr.istic.gm.weplan.infra.TestData.POSTAL_CODE;
import static fr.istic.gm.weplan.infra.TestData.someForecastHourly;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherClientTest {

    private WeatherClient weatherClient;

    @Mock
    private Class5Day3HourForecastApi mockClass5Day3HourForecastApi;

    private WeatherProperties fakeWeatherProperties;

    private WeatherMapper weatherMapper;

    @Before
    public void setUp() {
        fakeWeatherProperties = new WeatherProperties();
        fakeWeatherProperties.setApiKey(API_KEY);
        weatherMapper = Mappers.getMapper(WeatherMapper.class);
        weatherClient = new WeatherClientImpl(mockClass5Day3HourForecastApi, fakeWeatherProperties, weatherMapper);
    }

    @Test
    public void shouldRetrieveTheWeatherWeek() {

        ForecastHourly forecastHourly = someForecastHourly();

        when(mockClass5Day3HourForecastApi.forecast3hourlyGet(any(), any(), any(), any(), any(), any(), any())).thenReturn(forecastHourly);

        Week weatherWeek = weatherClient.getWeatherWeek(POSTAL_CODE);

        verify(mockClass5Day3HourForecastApi).forecast3hourlyGet(POSTAL_CODE, API_KEY, "FR", null, "M", "fr", null);

        assertThat(weatherWeek, notNullValue());
        assertThat(weatherWeek.getWeathers(), notNullValue());
        assertThat(weatherWeek.getWeathers(), hasSize(forecastHourly.getData().size()));
        assertThat(weatherWeek.getWeathers().get(0).getCode(), equalTo(forecastHourly.getData().get(0).getWeather().getCode()));
        assertThat(weatherWeek.getWeathers().get(0).getDescription(), equalTo(forecastHourly.getData().get(0).getWeather().getDescription()));
        assertThat(weatherWeek.getWeathers().get(0).getIcon(), equalTo(forecastHourly.getData().get(0).getWeather().getIcon()));
    }
}
