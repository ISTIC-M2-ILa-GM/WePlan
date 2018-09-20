package fr.istic.gm.weplan.domain.model.request;

import lombok.Data;

/**
 * The city request
 */
@Data
public class CityRequest {
    private String name;
    private Integer postalCode;
    private Long departmentId;
}
