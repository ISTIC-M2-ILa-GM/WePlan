package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.entities.Event;

/**
 * Event Dao Service
 */
public interface EventDaoService {

    /**
     * Get a event dao for other services.
     *
     * @param id the id to get
     * @return the event
     */
    Event getEventDao(Long id);
}
