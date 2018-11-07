package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.entities.City;

import java.util.List;

public interface CityDaoService {

    /**
     * Get a city dao for other services.
     *
     * @param id the id to get
     * @return the city
     */
    City getCityDao(Long id);

    List<City> getCitiesDao();
}