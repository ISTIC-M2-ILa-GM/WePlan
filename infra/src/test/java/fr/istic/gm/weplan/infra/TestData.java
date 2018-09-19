package fr.istic.gm.weplan.infra;

import fr.istic.gm.weplan.domain.model.entities.City;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class TestData {

    private static final PodamFactory FACTORY = new PodamFactoryImpl();

    public static City someCity() {
        City city = FACTORY.manufacturePojoWithFullData(City.class);
        city.setDeletedAt(null);
        return city;
    }

}
