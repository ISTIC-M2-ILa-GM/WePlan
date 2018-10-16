package fr.istic.gm.weplan.server.websocket;

import fr.istic.gm.weplan.domain.model.dto.EventDto;
import fr.istic.gm.weplan.infra.broker.EventBroker;
import fr.istic.gm.weplan.server.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.GenericMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static fr.istic.gm.weplan.infra.broker.impl.EventBrokerImpl.EVENT;
import static fr.istic.gm.weplan.infra.broker.impl.EventBrokerImpl.STOMP;
import static fr.istic.gm.weplan.server.TestData.someEvent;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketIT {

    private static final String URI = "ws://localhost:{port}/" + STOMP;

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private EventBroker eventBroker;

    private StompSession session;

    private List<Object> payloads = new ArrayList<>();

    private CountDownLatch lock = new CountDownLatch(1);

    @Before
    public void setUp() throws InterruptedException, ExecutionException {

        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(singletonList(new WebSocketTransport(new StandardWebSocketClient()))));
        stompClient.setMessageConverter(new GenericMessageConverter());
        session = stompClient.connect(URI, new StompSessionHandlerAdapter() {
        }, port).get();
    }

    @Test
    public void shouldSendAnEvent() throws InterruptedException {

        session.subscribe(EVENT, new StompSessionHandlerAdapter() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return byte[].class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                payloads.add(JsonUtils.parse(new String((byte[]) payload), EventDto.class));
                lock.countDown();
            }
        });

        EventDto event = someEvent();

        eventBroker.sendEvent(event);

        lock.await(5000, TimeUnit.MILLISECONDS);

        assertThat(payloads, hasSize(1));
        assertThat(payloads.get(0), Matchers.instanceOf(EventDto.class));
        assertThat(payloads.get(0), equalTo(event));
    }
}
