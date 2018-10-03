package fr.istic.gm.weplan.infra.client.weather.mapper;

import fr.istic.gm.weplan.domain.model.weather.Weather;
import fr.istic.gm.weplan.domain.model.weather.Week;
import fr.istic.gm.weplan.infra.client.weather.generated.api.model.ForecastHour;
import fr.istic.gm.weplan.infra.client.weather.generated.api.model.ForecastHourly;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * The weather mapper
 */
@Mapper(componentModel = "spring", imports = {Instant.class})
public interface WeatherMapper {

    DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd:H")
            .toFormatter()
            .withZone(ZoneOffset.UTC);

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
            @Mapping(target = "date", expression = "java(DATE_FORMAT.parse(forecastHour.getDatetime(), Instant::from))")
    })
    Weather toWeather(ForecastHour forecastHour);
}
