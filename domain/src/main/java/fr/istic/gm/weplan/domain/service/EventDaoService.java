package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.entities.Event;
import fr.istic.gm.weplan.domain.model.request.PageRequest;
import org.springframework.data.domain.Page;

/**
 * Event Dao Service
 */
public interface EventDaoService {

    /**
     * Get events dao with a page request.
     * @param pageRequest the page request
     * @return the events dao page
     */
    Page<Event> getEventsDao(PageRequest pageRequest);

    /**
     * Get a event dao for other services.
     *
     * @param id the id to get
     * @return the event
     */
    Event getEventDao(Long id);
}
