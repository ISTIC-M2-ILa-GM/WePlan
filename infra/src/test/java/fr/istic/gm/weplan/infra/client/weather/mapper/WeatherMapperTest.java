package fr.istic.gm.weplan.infra.client.weather.mapper;

import fr.istic.gm.weplan.domain.model.weather.Weather;
import fr.istic.gm.weplan.domain.model.weather.Week;
import fr.istic.gm.weplan.infra.client.weather.api.model.ForecastHour;
import fr.istic.gm.weplan.infra.client.weather.api.model.ForecastHourly;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static fr.istic.gm.weplan.infra.TestData.someForecastHour;
import static fr.istic.gm.weplan.infra.TestData.someForecastHourly;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

public class WeatherMapperTest {

    private WeatherMapper mapper;

    @Before
    public void setUp() {
        mapper = Mappers.getMapper(WeatherMapper.class);
    }

    @Test
    public void shouldMapToAWeek() {
        assertThat(mapper.toWeek(null), nullValue());

        ForecastHourly forecastHourly = someForecastHourly();

        Week week = mapper.toWeek(forecastHourly);

        assertThat(week, notNullValue());
        assertThat(week.getWeathers(), notNullValue());
        assertThat(week.getWeathers(), Matchers.not(empty()));
        assertThat(week.getWeathers(), hasSize(forecastHourly.getData().size()));
        assertThat(week.getWeathers().get(0).getCode(), equalTo(forecastHourly.getData().get(0).getWeather().getCode()));
        assertThat(week.getWeathers().get(0).getDescription(), equalTo(forecastHourly.getData().get(0).getWeather().getDescription()));
        assertThat(week.getWeathers().get(0).getIcon(), equalTo(forecastHourly.getData().get(0).getWeather().getIcon()));
    }

    @Test
    public void shouldMapToAWeather() {
        assertThat(mapper.toWeather(null), nullValue());

        ForecastHour forecastHour = someForecastHour();

        Weather weather = mapper.toWeather(forecastHour);

        assertThat(weather, notNullValue());
        assertThat(weather.getCode(), equalTo(forecastHour.getWeather().getCode()));
        assertThat(weather.getDescription(), equalTo(forecastHour.getWeather().getDescription()));
        assertThat(weather.getIcon(), equalTo(forecastHour.getWeather().getIcon()));
    }
}
