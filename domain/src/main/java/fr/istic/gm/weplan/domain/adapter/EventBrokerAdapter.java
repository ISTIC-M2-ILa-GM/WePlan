package fr.istic.gm.weplan.domain.adapter;

import fr.istic.gm.weplan.domain.model.dto.EventDto;

/**
 * The event broker adapter
 */
public interface EventBrokerAdapter {

    /**
     * Send an event to the event broker
     *
     * @param event the event
     */
    void sendEvent(EventDto event);
}
