package fr.istic.gm.weplan.domain.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * The city request
 */
@Data
public class ActivityRequest {

    @NotEmpty
    private String name;

    private Float cost;

    private boolean outDoor;

    @NotEmpty
    private String activityType;

    @NotEmpty
    private List<Long> citiesId;
}
