package fr.istic.gm.weplan.server;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Collections;

public class TestData {

    private static final PodamFactory FACTORY = new PodamFactoryImpl();

    public static PageOptions somePageOptions() {
        return FACTORY.manufacturePojoWithFullData(PageOptions.class);
    }

    public static PageDto<CityDto> somePageCities() {
        PageDto<CityDto> cityPageDto = new PageDto<>();
        cityPageDto.setPage(10);
        cityPageDto.setSize(10);
        cityPageDto.setResults(Collections.singletonList(someCity()));
        return cityPageDto;
    }

    public static CityDto someCity() {
        return FACTORY.manufacturePojoWithFullData(CityDto.class);
    }
}
