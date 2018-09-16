package fr.istic.gm.weplan.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;

import static fr.istic.gm.weplan.domain.config.PersistsDefinition.ACTIVITY;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.CANCELED;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.CITY;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.DATE;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.EVENT;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = EVENT)
public class Event extends BaseEntity {

    @Column(name = CANCELED)
    private boolean canceled;

    @Column(name = DATE)
    private Instant date;

    @OneToMany
    @Column(name = ACTIVITY)
    private Activity activity;

    @OneToMany
    @Column(name = CITY)
    private City city;
}
