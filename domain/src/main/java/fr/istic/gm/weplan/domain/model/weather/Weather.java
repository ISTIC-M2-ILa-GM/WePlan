package fr.istic.gm.weplan.domain.model.weather;

import lombok.Data;

import java.time.Instant;

@Data
public class Weather {

    private String icon;
    private String code;
    private String description;
    private Instant date;
}
