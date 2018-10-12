package fr.istic.gm.weplan.domain.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * The department request
 */
@Data
public class DepartmentRequest {

    @NotNull
    private String name;

    @NotNull
    private int code;

    @NotNull
    private Long regionId;
}
