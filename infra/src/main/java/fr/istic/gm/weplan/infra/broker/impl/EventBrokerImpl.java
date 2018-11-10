package fr.istic.gm.weplan.infra.broker.impl;

import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.infra.broker.EventBroker;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EventBrokerImpl implements EventBroker {

    public static final String STOMP = "/stomp";
    public static final String WS = "/ws";
    public static final String EVENT = "/event";

    private SimpMessageSendingOperations simpMessageSendingOperations;

    @Override
    public void sendEvent(EventDto event) {
        simpMessageSendingOperations.convertAndSend(EVENT, event);
    }
}
