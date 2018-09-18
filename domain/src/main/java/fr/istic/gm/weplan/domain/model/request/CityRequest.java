package fr.istic.gm.weplan.domain.model.request;

import lombok.Data;

@Data
public class CityRequest {
    private String name;
    private Integer postalCode;
    private Long departmentId;
}
