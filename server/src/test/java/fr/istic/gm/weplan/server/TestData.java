package fr.istic.gm.weplan.server;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Collections;

public class TestData {

    public static final Long ID = 10L;

    private static final PodamFactory FACTORY = new PodamFactoryImpl();

    public static Department someDepartmentDao() {
        Department department = FACTORY.manufacturePojoWithFullData(Department.class);
        department.setDeletedAt(null);
        return department;
    }

    public static City someCityDao() {
        return FACTORY.manufacturePojoWithFullData(City.class);
    }

    public static CityRequest someCityRequest() {
        return FACTORY.manufacturePojoWithFullData(CityRequest.class);
    }

    public static PageOptions somePageOptions() {
        return FACTORY.manufacturePojoWithFullData(PageOptions.class);
    }

    public static PageDto<CityDto> somePageCities() {
        PageDto<CityDto> citiesDto = new PageDto<>();
        citiesDto.setSize(10);
        citiesDto.setTotalPages(10);
        citiesDto.setResults(Collections.singletonList(someCity()));
        return citiesDto;
    }

    public static CityDto someCity() {
        return FACTORY.manufacturePojoWithFullData(CityDto.class);
    }

    public static PageDto<RegionDto> somePageRegions() {
        PageDto<RegionDto> regionsDto = new PageDto<>();
        regionsDto.setSize(10);
        regionsDto.setTotalPages(10);
        regionsDto.setResults(Collections.singletonList(someRegion()));
        return regionsDto;
    }

    public static RegionDto someRegion() {
        return FACTORY.manufacturePojoWithFullData(RegionDto.class);
    }

    public static Region someRegionDao() {
        return FACTORY.manufacturePojoWithFullData(Region.class);
    }
}
