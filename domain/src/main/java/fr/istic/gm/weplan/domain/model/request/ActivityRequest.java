package fr.istic.gm.weplan.domain.model.request;

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
    private String activityType;
    private List<Long> citiesId;
}
