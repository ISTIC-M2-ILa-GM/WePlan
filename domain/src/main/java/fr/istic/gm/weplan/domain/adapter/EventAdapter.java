package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Optional;

/**
 * The event adapter
 */
public interface EventAdapter {

    /**
     * Find all events with pageable.
     *
     * @param pageable the pageable
     * @return the event page
     */
    Page<Event> findAll(Pageable pageable);

    /**
     * Find all events which is not deleted with pageable.
     *
     * @param pageable the pageable
     * @return the event page
     */
    Page<Event> findAllByDeletedAtIsNullAndDateAfterOrderByDateAsc(Pageable pageable, Instant date);

    /**
     * Find an event by id.
     *
     * @param id the id to search
     * @return the optional event
     */
    Optional<Event> findById(Long id);

    /**
     * Save an event.
     *
     * @param event the event to save
     * @return the saved event.
     */
    Event save(Event event);
}
