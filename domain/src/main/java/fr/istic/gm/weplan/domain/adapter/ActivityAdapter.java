package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ActivityAdapter {
    Page<Activity> findAll(Pageable pageable);

    Optional<Activity> findById(Long id);

    Activity save(Activity activity);

    Page<Activity> findAllByDeletedAtIsNull(Pageable pageable);
}
