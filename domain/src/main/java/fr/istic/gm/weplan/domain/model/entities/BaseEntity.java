package fr.istic.gm.weplan.domain.model.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

import static fr.istic.gm.weplan.domain.config.PersistsDefinition.CREATED_AT;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.DELETED_AT;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.UPDATED_AT;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    @Column(name = CREATED_AT)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = UPDATED_AT)
    private Instant updatedAt;

    @Column(name = DELETED_AT)
    private Instant deletedAt;
}
