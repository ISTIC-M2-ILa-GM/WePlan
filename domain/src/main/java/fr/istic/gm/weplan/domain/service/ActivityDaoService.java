package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.entities.Activity;

import java.util.List;

public interface ActivityDaoService {

    /**
     * Get an activity dao for other services.
     *
     * @param id the id to get
     * @return the activity
     */
    Activity getActivityDao(Long id);

    /**
     * Get all activities dao
     *
     * @return the activities
     */
    List<Activity> getActivitiesDao();
}
