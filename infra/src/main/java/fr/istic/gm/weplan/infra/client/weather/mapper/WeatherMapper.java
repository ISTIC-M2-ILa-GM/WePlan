package fr.istic.gm.weplan.infra.client.weather.mapper;

import fr.istic.gm.weplan.domain.model.weather.Weather;
import fr.istic.gm.weplan.domain.model.weather.Week;
import fr.istic.gm.weplan.infra.client.weather.api.model.ForecastHour;
import fr.istic.gm.weplan.infra.client.weather.api.model.ForecastHourly;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

    @Mappings({
            @Mapping(source = "data", target = "weathers")
    })
    Week toWeek(ForecastHourly forecastHourly);

    @Mappings({
            @Mapping(source = "weather.icon", target = "icon"),
            @Mapping(source = "weather.code", target = "code"),
            @Mapping(source = "weather.description", target = "description")
    })
    Weather toWeather(ForecastHour forecastHour);
}
