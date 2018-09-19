package fr.istic.gm.weplan.infra.repository;

import fr.istic.gm.weplan.domain.adapter.CityAdapter;
import fr.istic.gm.weplan.domain.model.entities.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The City repository
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long>, CityAdapter {

    @Override
    Page<City> findAll(Pageable pageable);

    @Override
    Page<City> findAllByDeletedAtIsNull(Pageable pageable);

    @Override
    Optional<City> findById(Long id);

    @Override
    City save(City city);
}
