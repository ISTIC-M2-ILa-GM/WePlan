package fr.istic.gm.weplan.domain.service;

import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.domain.model.dto.PageDto;
import fr.istic.gm.weplan.domain.model.dto.PageOptions;

/**
 * Event Service
 */
public interface EventService {

    /**
     * Retrieve all events.
     *
     * @param pageOptions the page options
     * @return The pages of Events
     */
    PageDto<EventDto> getEvents(PageOptions pageOptions);

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
}
