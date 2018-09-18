package fr.istic.gm.weplan.infra.repository.impl;

import fr.istic.gm.weplan.domain.adapter.CityAdapter;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.infra.repository.utils.EntityManagerHelper;

import javax.persistence.EntityManager;

import static fr.istic.gm.weplan.domain.config.PersistsDefinition.CITY;

public class CityRepositoryImpl extends BaseRepository<City> implements CityAdapter {

    public CityRepositoryImpl() {
        super(City.class, CITY, EntityManagerHelper.getEntityManager());
    }

    public CityRepositoryImpl(EntityManager entityManager) {
        super(City.class, CITY, entityManager);
    }
}
