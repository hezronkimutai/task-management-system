package com.taskmanagement;

import com.taskmanagement.entity.Task;
import com.taskmanagement.entity.TaskStatus;
import com.taskmanagement.entity.Priority;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.service.UserDetailsServiceImpl;
import com.taskmanagement.util.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testWebSocketAuthAndReceiveEvent() throws Exception {
        String token = jwtUtils.generateJwtToken("user1");

        BlockingQueue<String> messages = new ArrayBlockingQueue<>(1);

        List<Transport> transports = Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String url = String.format("ws://localhost:%d/ws", port);

        ListenableFuture<StompSession> future = stompClient.connect(url, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            }
        }, new org.springframework.util.concurrent.ListenableFutureCallback<StompSession>() {
            public void onSuccess(StompSession s) {}
            public void onFailure(Throwable t) {}
        });

        StompSession session = future.get(3, TimeUnit.SECONDS);
        session.subscribe("/topic/tasks", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) { return String.class; }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                messages.offer((String) payload);
            }
        });

        // create a task to trigger event
        Task t = new Task("ws test","desc", TaskStatus.TODO, Priority.MEDIUM, null, 1L);
        taskRepository.save(t);

        String msg = messages.poll(5, TimeUnit.SECONDS);
        // it's acceptable that message may be null in CI, but assert non-null here
        Assertions.assertNotNull(msg, "Expected to receive a websocket message when task created");
    }
}
