package fr.istic.gm.weplan.domain.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import static fr.istic.gm.weplan.domain.config.PersistsDefinition.CITY;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.DEPARTMENT;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.NAME;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.POSTAL_CODE;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = CITY)
public class City extends BaseEntity {

    @Column(name = NAME)
    private String name;

    @Column(name = POSTAL_CODE)
    private Integer postalCode;

    @OneToMany
    @Column(name = DEPARTMENT)
    private Department department;
}
