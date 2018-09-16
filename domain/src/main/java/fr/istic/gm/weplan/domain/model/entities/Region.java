package fr.istic.gm.weplan.domain.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static fr.istic.gm.weplan.domain.config.PersistsDefinition.NAME;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.REGION;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = REGION)
public class Region extends BaseEntity {

    @Column(name = NAME)
    private String name;
}
