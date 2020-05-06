package com.vdobrikov.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Objects;
import java.util.function.Consumer;

public class StompWebSocketClient {
    private static final Logger LOG = LoggerFactory.getLogger(StompWebSocketClient.class);

    private final String url;
    private final Consumer<String> messageHandler;

    public StompWebSocketClient(String url, Consumer<String> messageHandler) {
        this.url = Objects.requireNonNull(url);
        this.messageHandler = Objects.requireNonNull(messageHandler);
    }

    public void connect() {
        LOG.info("Connecting to url={}", url);
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new StringMessageConverter());

        StompSessionHandler sessionHandler = new WebSocketSessionHandler(messageHandler);
        stompClient.connect(url, sessionHandler);

    }
}
