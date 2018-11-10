package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.weather.Week;

/**
 * The weather adapter
 */
public interface WeatherAdapter {

    /**
     * Retrieve a week of weather
     *
     * @param postalCode the postal code
     * @return the week of weather
     */
    Week getWeatherWeek(int postalCode);
}
