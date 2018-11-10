package fr.istic.gm.weplan.infra.repository;

import fr.istic.gm.weplan.domain.adapter.EventAdapter;
import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * The Event repository
 */
public interface EventRepository extends JpaRepository<Event, Long>, EventAdapter {

    @Override
    Page<Event> findAll(Pageable pageable);

    @Override
    @Query("SELECT e FROM Event as e WHERE e.deletedAt IS NULL AND e.date > current_date AND e.city IN :cities AND e.activity IN :activities ORDER BY e.date")
    Page<Event> findAllByCitiesAndActivities(Pageable pageable, @Param("cities") List<City> cities, @Param("activities") List<Activity> activities);

    @Override
    Optional<Event> findById(Long id);

    @Override
    <S extends Event> S save(S event);
}
