package fr.istic.gm.weplan.infra;

import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Department;
import fr.istic.gm.weplan.infra.client.weather.api.model.ForecastHourly;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class TestData {

    private static final PodamFactory FACTORY = new PodamFactoryImpl();
    public static final int POSTAL_CODE = 35000;
    public static final String API_KEY = "an-api-key";

    public static ForecastHourly someForecastHourly() {
        return FACTORY.manufacturePojoWithFullData(ForecastHourly.class);
    }

    public static Department someDepartment() {
        Department department = FACTORY.manufacturePojoWithFullData(Department.class);
        department.setDeletedAt(null);
        return department;
    }

    public static City someCity() {
        City city = FACTORY.manufacturePojoWithFullData(City.class);
        city.setDeletedAt(null);
        return city;
    }

}
