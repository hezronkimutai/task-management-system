package com.taskmanagement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketAuthFailureTest {

    @LocalServerPort
    private int port;

    @Test
    public void connectWithoutTokenShouldFail() throws Exception {
        List<Transport> transports = Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

        String url = String.format("ws://localhost:%d/ws", port);

        // Attempt connect without Authorization header. Expect exception or failure to connect within timeout.
        ListenableFuture<StompSession> future = stompClient.connect(url, new StompSessionHandlerAdapter() {});
        try {
            StompSession session = future.get(3, TimeUnit.SECONDS);
            // if connected, fail the test
            Assertions.fail("Expected connection to be rejected, but it succeeded");
        } catch (Exception e) {
            // expected path: connection fails
            Assertions.assertTrue(true);
        }
    }
}
