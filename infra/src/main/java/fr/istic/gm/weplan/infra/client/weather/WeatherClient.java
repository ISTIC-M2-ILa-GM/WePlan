package fr.istic.gm.weplan.infra.client.weather;

import fr.istic.gm.weplan.domain.adapter.WeatherAdapter;
import fr.istic.gm.weplan.domain.model.weather.Week;

/**
 * The weather client
 */
public interface WeatherClient extends WeatherAdapter {

    /**
     * Retrieve a week of weather
     *
     * @param postalCode the postal code
     * @return the week of weather
     */
    Week getWeatherWeek(int postalCode);
}
