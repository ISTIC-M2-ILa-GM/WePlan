package fr.istic.gm.weplan.infra.client.weather.impl;

import fr.istic.gm.weplan.domain.model.weather.Week;
import fr.istic.gm.weplan.infra.client.weather.WeatherClient;
import fr.istic.gm.weplan.infra.client.weather.api.Class5Day3HourForecastApi;
import fr.istic.gm.weplan.infra.client.weather.api.model.ForecastHourly;
import fr.istic.gm.weplan.infra.client.weather.config.properties.WeatherProperties;
import fr.istic.gm.weplan.infra.client.weather.mapper.WeatherMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class WeatherClientImpl implements WeatherClient {

    private Class5Day3HourForecastApi class5Day3HourForecastApi;

    private WeatherProperties weatherProperties;

    private WeatherMapper weatherMapper;

    @Override
    public Week getWeatherWeek(int postalCode) {
        ForecastHourly forecastHourly = class5Day3HourForecastApi.forecast3hourlyGet(postalCode, weatherProperties.getApiKey(), "FR", null, "M", "fr", null);
        return weatherMapper.toWeek(forecastHourly);
    }
}
