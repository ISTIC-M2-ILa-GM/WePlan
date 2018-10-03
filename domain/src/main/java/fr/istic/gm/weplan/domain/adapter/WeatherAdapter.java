package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.weather.Week;

public interface WeatherAdapter {
    Week getWeatherWeek(int postalCode);
}
