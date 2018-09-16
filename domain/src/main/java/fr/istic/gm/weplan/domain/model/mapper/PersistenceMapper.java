package fr.istic.gm.weplan.domain.model.mapper;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.entities.City;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersistenceMapper {

    CityDto toCityDto(City city);

    List<CityDto> toCitiesDto(List<City> cities);
}