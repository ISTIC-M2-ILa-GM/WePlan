package fr.istic.gm.weplan.domain.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageDto<T> {
    private int size;
    private int totalPages;
    private List<T> results;
}
