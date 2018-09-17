package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.entities.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * The city adapter
 */
public interface CityAdapter {

    /**
     * Find all city with pageable
     * @param pageable the pageable
     * @return the city page
     */
    Page<City> findAll(Pageable pageable);

    /**
     * Find a city by id
     * @param id the id to search
     * @return the optional city
     */
    Optional<City> findById(Long id);
}
