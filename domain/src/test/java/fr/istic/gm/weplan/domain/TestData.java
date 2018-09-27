package fr.istic.gm.weplan.domain;

import fr.istic.gm.weplan.domain.model.dto.CityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.domain.model.entities.Region;
import fr.istic.gm.weplan.domain.model.request.ActivityRequest;
import fr.istic.gm.weplan.domain.model.request.CityRequest;
import fr.istic.gm.weplan.domain.model.request.DepartmentRequest;
import fr.istic.gm.weplan.domain.model.request.RegionRequest;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Collections;

public class TestData {

    public static final Long ID = 10L;

    private static final PodamFactory FACTORY = new PodamFactoryImpl();

    public static Department someDepartment() {
        Department department = FACTORY.manufacturePojoWithFullData(Department.class);
        department.setDeletedAt(null);
        return department;
    }

    public static DepartmentRequest someDepartmentRequest() {
        return FACTORY.manufacturePojoWithFullData(DepartmentRequest.class);
    }

    public static Region someRegion() {
        Region region = FACTORY.manufacturePojoWithFullData(Region.class);
        region.setDeletedAt(null);
        return region;
    }

    public static Activity someActivity() {
        Activity activity = FACTORY.manufacturePojoWithFullData(Activity.class);
        activity.setDeletedAt(null);
        return activity;
    }

    public static ActivityRequest someActivityRequest() {
        return FACTORY.manufacturePojoWithFullData(ActivityRequest.class);
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
        City city = FACTORY.manufacturePojoWithFullData(City.class);
        city.setDeletedAt(null);
        return city;
    }

    public static RegionRequest someRegionRequest() {
        return FACTORY.manufacturePojoWithFullData(RegionRequest.class);
    }
}
