package fr.istic.gm.weplan.domain.model.mapper;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersistenceMapper {

    CityDto toCityDto(City city);

    List<CityDto> toCitiesDto(List<City> cities);

    City toCity(CityRequest cityRequest);

    DepartmentDto toDepartmentDto(Department department);

    default PageDto<CityDto> toCitiesPageDto(Page<City> cities) {
        if (cities == null) {
            return null;
        }
        PageDto<CityDto> citiesDto = new PageDto<>();
        citiesDto.setSize(cities.getSize());
        citiesDto.setTotalPages(cities.getTotalPages());
        citiesDto.setResults(toCitiesDto(cities.getContent()));
        return citiesDto;
    }
}
