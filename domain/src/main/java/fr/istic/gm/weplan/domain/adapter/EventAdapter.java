package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.entities.Activity;
import fr.istic.gm.weplan.domain.model.entities.City;
import fr.istic.gm.weplan.domain.model.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
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
     * Find all event by cities and activities with pageable.
     *
     * @param pageable   the pageable
     * @param cities     the cities
     * @param activities the activities
     * @return the event page
     */
    Page<Event> findAllByCitiesAndActivities(Pageable pageable, List<City> cities, @Param("activities") List<Activity> activities);

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
    <S extends Event> S save(S event);
}
