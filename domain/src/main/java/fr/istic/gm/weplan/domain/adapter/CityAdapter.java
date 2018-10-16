package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.entities.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * The city adapter
 */
public interface CityAdapter {

    /**
     * Find all city with pageable.
     *
     * @param pageable the pageable
     * @return the cities page
     */
    Page<City> findAll(Pageable pageable);

    /**
     * Find all city which is not deleted with pageable.
     *
     * @param pageable the pageable
     * @return the city page
     */
    Page<City> findAllByDeletedAtIsNull(Pageable pageable);

    /**
     * Find all city which is not deleted.
     *
     * @return the cities
     */
    List<City> findAllByDeletedAtIsNull();

    /**
     * Find a city by id.
     *
     * @param id the id to search
     * @return the optional city
     */
    Optional<City> findById(Long id);

    /**
     * Save a city.
     *
     * @param city the city to save
     * @return the saved city.
     */
    City save(City city);
}
