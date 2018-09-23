package fr.istic.gm.weplan.domain.model.mapper;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.DepartmentDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.model.request.DepartmentRequest;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersistenceMapper {

    CityDto toCityDto(City city);

    List<CityDto> toCitiesDto(List<City> cities);

    RegionDto toRegionDto(Region region);

    List<RegionDto> toRegionsDto(List<Region> regions);

    default PageDto<RegionDto> toRegionsPageDto(Page<Region> regions) {
        if (regions == null)
            return null;

        PageDto<RegionDto> regionsDto = new PageDto<>();
        regionsDto.setSize(regions.getSize());
        regionsDto.setTotalPages(regions.getTotalPages());
        regionsDto.setResults(toRegionsDto(regions.getContent()));
        return regionsDto;
    }

    Region toRegion(RegionRequest regionDto);

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

    Department toDepartment(DepartmentRequest departmentRequest);

    List<DepartmentDto> toDepartmentsDto(List<Department> departments);

    default PageDto<DepartmentDto> toDepartmentsPageDto(Page<Department> departments) {
        if (departments == null) {
            return null;
        }
        PageDto<DepartmentDto> departmentsDto = new PageDto<>();
        departmentsDto.setSize(departments.getSize());
        departmentsDto.setTotalPages(departments.getTotalPages());
        departmentsDto.setResults(toDepartmentsDto(departments.getContent()));
        return departmentsDto;
    }
}
