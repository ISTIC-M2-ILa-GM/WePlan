package fr.istic.gm.weplan.domain.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static fr.istic.gm.weplan.domain.config.PersistsDefinition.CODE;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.DEPARTMENT;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.NAME;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = DEPARTMENT)
public class Department extends BaseEntity {

    @Column(name = NAME)
    private String name;

    @Column(name = CODE)
    private int code;

    @ManyToOne
    private Region region;
}
