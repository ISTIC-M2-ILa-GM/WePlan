package fr.istic.gm.weplan.domain.model.request;

import lombok.Data;

@Data
public class PageRequest {
    private int size;
    private int page;
}
