package fr.istic.gm.weplan.domain.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static fr.istic.gm.weplan.domain.config.PersistsDefinition.ACTIVITY;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.ACTIVITY_TYPE;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.CITIES;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.COST;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.NAME;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.OUT_DOOR;

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
