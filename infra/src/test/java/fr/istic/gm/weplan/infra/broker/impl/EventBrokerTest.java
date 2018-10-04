package fr.istic.gm.weplan.infra.broker.impl;

import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.infra.broker.EventBroker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import static fr.istic.gm.weplan.infra.TestData.someEventDto;
import static fr.istic.gm.weplan.infra.broker.impl.EventBrokerImpl.EVENT;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EventBrokerTest {

    private EventBroker eventBroker;

    @Mock
    private SimpMessageSendingOperations mockSimpMessageSendingOperations;

    @Before
    public void setUp() {
        eventBroker = new EventBrokerImpl(mockSimpMessageSendingOperations);
    }

    @Test
    public void shouldSendAnEvent() {

        EventDto event = someEventDto();

        eventBroker.sendEvent(event);

        verify(mockSimpMessageSendingOperations).convertAndSend(EVENT, event);
    }
}
