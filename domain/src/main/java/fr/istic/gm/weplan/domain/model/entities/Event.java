package fr.istic.gm.weplan.domain.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

import static fr.istic.gm.weplan.domain.config.PersistsDefinition.CANCELED;
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

    @ManyToOne
    private Activity activity;

    @ManyToOne
    private City city;
}
