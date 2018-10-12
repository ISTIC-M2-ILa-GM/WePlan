package fr.istic.gm.weplan.domain.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The city request
 */
@Data
public class CityRequest {

    @NotEmpty
    private String name;

    @NotNull
    private Integer postalCode;

    @NotNull
    private Long departmentId;
}
