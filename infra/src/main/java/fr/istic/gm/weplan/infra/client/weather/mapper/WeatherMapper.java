package fr.istic.gm.weplan.infra.client.weather.mapper;

import fr.istic.gm.weplan.domain.model.weather.Weather;
import fr.istic.gm.weplan.domain.model.weather.Week;
import fr.istic.gm.weplan.infra.client.weather.generated.api.model.ForecastHour;
import fr.istic.gm.weplan.infra.client.weather.generated.api.model.ForecastHourly;
import fr.istic.gm.weplan.infra.utils.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.Instant;

/**
 * The weather mapper
 */
@Mapper(componentModel = "spring", imports = {Instant.class, DateUtils.class})
public interface WeatherMapper {

    /**
     * Map an api data to week data
     *
     * @param forecastHourly the api data
     * @return the week data
     */
    @Mappings({
            @Mapping(source = "data", target = "weathers")
    })
    Week toWeek(ForecastHourly forecastHourly);

    /**
     * Map an api data to weather data
     *
     * @param forecastHour the api data
     * @return the week data
     */
    @Mappings({
            @Mapping(source = "weather.icon", target = "icon"),
            @Mapping(source = "weather.code", target = "code"),
            @Mapping(source = "weather.description", target = "description"),
            @Mapping(target = "date", expression = "java(DateUtils.WEATHER_DATE_FORMAT.parse(forecastHour.getDatetime(), Instant::from))")
    })
    Weather toWeather(ForecastHour forecastHour);
}
