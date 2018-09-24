package fr.istic.gm.weplan.domain.model.request;

import fr.istic.gm.weplan.domain.model.entities.ActivityType;
import lombok.Data;

import java.util.List;

/**
 * The city request
 */
@Data
public class ActivityRequest {
    private String name;
    private Float cost;
    private boolean outDoor;
    private ActivityType activityType;
    private List<Long> citiesIds;
}
