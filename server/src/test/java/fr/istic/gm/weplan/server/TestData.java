package fr.istic.gm.weplan.server;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
<<<<<<< HEAD
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.entities.Region;
=======
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
>>>>>>> feature/6_add-city-api
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Collections;

public class TestData {

    public static final Long ID = 10L;

    private static final PodamFactory FACTORY = new PodamFactoryImpl();

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
        return PageDto.<RegionDto>builder()
                .results(Collections.singletonList(someRegions()))
                .totalPages(10)
                .size(10)
                .build();
    }

    public static RegionDto someRegions() {
        return FACTORY.manufacturePojoWithFullData(RegionDto.class);
    }
}
