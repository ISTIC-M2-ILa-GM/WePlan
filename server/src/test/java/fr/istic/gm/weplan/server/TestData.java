package fr.istic.gm.weplan.server;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.dto.RegionDto;
import fr.istic.gm.weplan.domain.model.entities.Region;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Collections;

public class TestData {

    private static final PodamFactory FACTORY = new PodamFactoryImpl();

    public static PageOptions somePageOptions() {
        return FACTORY.manufacturePojoWithFullData(PageOptions.class);
    }

    public static PageDto<CityDto> somePageCities() {
        return PageDto.<CityDto>builder()
                .results(Collections.singletonList(someCity()))
                .totalPages(10)
                .size(10)
                .build();
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
