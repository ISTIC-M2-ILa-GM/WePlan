package fr.istic.gm.weplan.domain.model.request;

import lombok.Data;

/**
 * The department request
 */
@Data
public class DepartmentRequest {
    private String name;
    private int code;
    private Long regionId;
}
