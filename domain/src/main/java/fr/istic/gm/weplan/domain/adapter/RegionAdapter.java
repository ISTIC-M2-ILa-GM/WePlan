package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.entities.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RegionAdapter {
    Page<Region> findAll(Pageable pageable);
}
