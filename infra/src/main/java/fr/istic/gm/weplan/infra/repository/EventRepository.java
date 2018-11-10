package fr.istic.gm.weplan.infra.repository;

import fr.istic.gm.weplan.domain.adapter.EventAdapter;
import fr.istic.gm.weplan.domain.model.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

/**
 * The Event repository
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventAdapter {

    @Override
    Page<Event> findAll(Pageable pageable);

    @Override
    Page<Event> findAllByDeletedAtIsNullAndDateAfterOrderByDateAsc(Pageable pageable, Instant date);

    @Override
    Optional<Event> findById(Long id);

    @Override
    Event save(Event event);
}
