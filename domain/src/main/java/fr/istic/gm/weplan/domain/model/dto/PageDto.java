package fr.istic.gm.weplan.domain.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDto<T> {
    private int size;
    private int totalPages;
    private List<T> results;
}
