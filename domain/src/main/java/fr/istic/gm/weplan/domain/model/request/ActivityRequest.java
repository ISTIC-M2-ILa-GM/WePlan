package fr.istic.gm.weplan.domain.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * The city request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
