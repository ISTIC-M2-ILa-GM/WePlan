package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * The city adapter
 */
public interface ActivityAdapter {

    /**
     * Find all city with pageable.
     *
     * @param pageable the pageable
     * @return the activity page
     */
    Page<Activity> findAll(Pageable pageable);

    /**
     * Find all activities which is not deleted with pageable.
     *
     * @param pageable the pageable
     * @return the activity page
     */
    Page<Activity> findAllByDeletedAtIsNull(Pageable pageable);

    /**
     * Find an activity by id.
     *
     * @param id the id to search
     * @return the optional activity
     */
    Optional<Activity> findById(Long id);

    /**
     * Save a city.
     *
     * @param activity the city to save
     * @return the saved city.
     */
    Activity save(Activity activity);
}
