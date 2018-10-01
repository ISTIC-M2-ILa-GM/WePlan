package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.dto.ActivityDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;
import fr.istic.gm.weplan.domain.model.request.ActivityRequest;

import java.util.Map;

/**
 * City Service
 */
public interface ActivityService {

    /**
     * Retrieve all activities.
     *
     * @param pageOptions the page options
     * @return The pages of Activities
     */
    PageDto<ActivityDto> getActivities(PageOptions pageOptions);

    /**
     * Retrieve an activity.
     *
     * @param id the id of the activity
     * @return the activity
     */
    ActivityDto getActivity(Long id);

    /**
     * Create an activity.
     *
     * @param activityRequest the activity to create
     * @return the created activity
     */
    ActivityDto createActivity(ActivityRequest activityRequest);

    /**
     * Delete an activity.
     *
     * @param id the id of the activity to delete
     */
    void deleteActivity(Long id);

    /**
     * Patch an activity.
     *
     * @param id    the id of the activity to patch
     * @param patch the map of field to patch
     * @return the updated activity
     */
    ActivityDto patchActivity(Long id, Map<String, Object> patch);
}
