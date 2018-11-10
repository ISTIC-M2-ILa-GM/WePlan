package fr.istic.gm.weplan.domain.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EventDto extends BaseDto {

    private boolean canceled;
    private Instant date;
    private ActivityDto activity;
    private CityDto city;
}
