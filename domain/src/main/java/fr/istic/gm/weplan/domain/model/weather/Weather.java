package fr.istic.gm.weplan.domain.model.weather;

import lombok.Data;

@Data
public class Weather {

    private String icon;
    private String code;
    private String description;
}
