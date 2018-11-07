package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.City;

import java.util.List;

public interface ActivityDaoService {

    /**
     * Get a region dao for other services.
     * @param id the id to get
     * @return the activity
     */
    Activity getActivityDao(Long id);

    List<Activity> getActivitiesDao();
}
