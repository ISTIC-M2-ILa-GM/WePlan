package fr.istic.gm.weplan.infra.repository;

import fr.istic.gm.weplan.domain.adapter.ActivityAdapter;
import fr.istic.gm.weplan.domain.model.entities.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The City repository
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>, ActivityAdapter {

    @Override
    Page<Activity> findAll(Pageable pageable);

    @Override
    Page<Activity> findAllByDeletedAtIsNull(Pageable pageable);

    @Override
    Optional<Activity> findById(Long id);

    @Override
    Activity save(Activity activity);
}
