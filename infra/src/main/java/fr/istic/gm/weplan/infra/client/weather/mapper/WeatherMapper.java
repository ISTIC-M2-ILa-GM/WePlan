package fr.istic.gm.weplan.infra.client.weather.mapper;

import fr.istic.gm.weplan.domain.model.weather.Week;
import fr.istic.gm.weplan.infra.client.weather.api.model.ForecastHourly;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

    @Mappings({
            @Mapping(source = "data.weather", target = "weathers")
    })
    Week toWeek(ForecastHourly forecastHourly);
}
