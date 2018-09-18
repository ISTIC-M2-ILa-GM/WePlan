package fr.istic.gm.weplan.infra.repository;

import fr.istic.gm.weplan.domain.adapter.RegionAdapter;
import fr.istic.gm.weplan.domain.model.entities.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The City repository
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long>, RegionAdapter {

    @Override
    Page<Region> findAll(Pageable pageable);
}
