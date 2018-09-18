package fr.istic.gm.weplan.domain;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Collections;

public class TestData {

    public static final Long ID = 10L;

    private static final PodamFactory FACTORY = new PodamFactoryImpl();

    public static Department someDepartment() {
        return FACTORY.manufacturePojoWithFullData(Department.class);
    }

    public static CityRequest someCityRequest() {
        return FACTORY.manufacturePojoWithFullData(CityRequest.class);
    }

    public static PageOptions somePageOptions() {
        return FACTORY.manufacturePojoWithFullData(PageOptions.class);
    }

    public static PageDto<CityDto> somePageDtoCities() {
        PageDto<CityDto> citiesDto = new PageDto<>();
        citiesDto.setSize(10);
        citiesDto.setTotalPages(10);
        citiesDto.setResults(Collections.singletonList(someCityDto()));
        return citiesDto;
    }

    public static CityDto someCityDto() {
        return FACTORY.manufacturePojoWithFullData(CityDto.class);
    }

    public static City someCity() {
        return FACTORY.manufacturePojoWithFullData(City.class);
    }
}
