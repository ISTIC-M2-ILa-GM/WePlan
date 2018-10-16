package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.entities.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RegionAdapter {
    Page<Region> findAll(Pageable pageable);

    Optional<Region> findById(Long id);

    Region save(Region region);

    Page<Region> findAllByDeletedAtIsNull(Pageable pageable);

    List<Region> findAllByDeletedAtIsNull();
}
