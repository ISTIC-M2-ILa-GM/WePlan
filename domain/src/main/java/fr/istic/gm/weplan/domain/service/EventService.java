package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.entities.Event;
import fr.istic.gm.weplan.domain.model.request.PageRequest;

/**
 * Event Service
 */
public interface EventService {

    /**
     * Retrieve all events.
     *
     * @param pageRequest the page options
     * @return The pages of Events
     */
    PageDto<EventDto> getEvents(PageRequest pageRequest);

    /**
     * Retrieve a event.
     *
     * @param id the id of the event
     * @return the event
     */
    EventDto getEvent(Long id);

    /**
     * Delete a event.
     *
     * @param id the id of the event to delete
     */
    void deleteEvent(Long id);

    EventDto createEvent(Event event);
}
