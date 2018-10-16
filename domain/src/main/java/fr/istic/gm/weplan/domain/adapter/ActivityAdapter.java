package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.entities.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * The activity adapter
 */
public interface ActivityAdapter {

    /**
     * Find all activity with pageable.
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
     * Find all activities which is not deleted.
     *
     * @return the activity page
     */
    List<Activity> findAllByDeletedAtIsNull();

    /**
     * Find an activity by id.
     *
     * @param id the id to search
     * @return the optional activity
     */
    Optional<Activity> findById(Long id);

    /**
     * Save a activity.
     *
     * @param activity the activity to save
     * @return the saved activity.
     */
    Activity save(Activity activity);
}
