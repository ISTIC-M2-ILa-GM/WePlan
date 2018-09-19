package fr.istic.gm.weplan.domain.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
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
@Entity
@Table(name = ACTIVITY)
public class Activity extends BaseEntity {

    @Column(name = NAME)
    private String name;

    @Column(name = COST)
    private Float cost;

    @Column(name = OUT_DOOR)
    private boolean outDoor;

    @Enumerated(EnumType.STRING)
    @Column(name = ACTIVITY_TYPE)
    private ActivityType activityType;

    @ManyToMany
    @Column(name = CITIES)
    private List<City> cities;
}
