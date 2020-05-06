package com.vdobrikov.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.util.Objects;
import java.util.function.Consumer;

public class WebSocketSessionHandler extends StompSessionHandlerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketSessionHandler.class);
    private final Consumer<String> messageHandler;

    public WebSocketSessionHandler(Consumer<String> messageHandler) {
        this.messageHandler = Objects.requireNonNull(messageHandler);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        LOG.info("WS connected: sessionId={}", session.getSessionId());
        session.subscribe("/topic/messages", this);
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        LOG.info("WS message: payload={}", payload);
        String msg = (String) payload;
        messageHandler.accept(msg);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        LOG.error("Error", exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        LOG.error("Transport error", exception);
    }
}
