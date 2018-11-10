package fr.istic.gm.weplan.domain.model.dto;

import lombok.Data;

import java.time.Instant;

@Data
public abstract class BaseDto {

    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
