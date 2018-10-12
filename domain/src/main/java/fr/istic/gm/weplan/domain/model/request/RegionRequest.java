package fr.istic.gm.weplan.domain.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegionRequest {

    @NotNull
    private String name;
}
