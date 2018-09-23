package fr.istic.gm.weplan.domain.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ActivityDto extends BaseDto {

    private String name;
    private Float cost;
    private boolean outDoor;
    private ActivityTypeDto activityType;
    private List<CityDto> cities;
}
