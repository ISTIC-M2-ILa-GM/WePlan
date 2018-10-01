package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.entities.City;

public interface CityDaoService {

    /**
     * Get a city dao for other services.
     *
     * @param id the id to get
     * @return the city
     */
    City getCityDao(Long id);
}