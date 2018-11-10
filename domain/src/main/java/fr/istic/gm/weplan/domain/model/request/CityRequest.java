package fr.istic.gm.weplan.domain.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The city request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityRequest {

    @NotEmpty
    private String name;

    @NotNull
    private Integer postalCode;

    @NotNull
    private Long departmentId;
}
