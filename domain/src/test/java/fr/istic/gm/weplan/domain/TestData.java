package fr.istic.gm.weplan.domain;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.entities.City;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Collections;

public class TestData {

    private static final PodamFactory FACTORY = new PodamFactoryImpl();

    public static PageOptions somePageOptions() {
        return FACTORY.manufacturePojoWithFullData(PageOptions.class);
    }

    public static PageDto<CityDto> somePageDtoCities() {
        return PageDto.<CityDto>builder()
                .results(Collections.singletonList(someCityDto()))
                .totalPages(10)
                .size(10)
                .build();
    }

    public static CityDto someCityDto() {
        return FACTORY.manufacturePojoWithFullData(CityDto.class);
    }

    public static City someCity() {
        return FACTORY.manufacturePojoWithFullData(City.class);
    }
}
