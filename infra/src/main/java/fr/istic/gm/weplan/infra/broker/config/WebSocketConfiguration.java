package fr.istic.gm.weplan.infra.broker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static fr.istic.gm.weplan.infra.broker.impl.EventBrokerImpl.EVENT;
import static fr.istic.gm.weplan.infra.broker.impl.EventBrokerImpl.STOMP;
import static fr.istic.gm.weplan.infra.broker.impl.EventBrokerImpl.WS;


/**
 * The web socket configuration
 */
@EnableWebSocketMessageBroker
@EnableWebSocket
@Configuration
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes(WS)
                .enableSimpleBroker(EVENT);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(STOMP).withSockJS();
    }
}
