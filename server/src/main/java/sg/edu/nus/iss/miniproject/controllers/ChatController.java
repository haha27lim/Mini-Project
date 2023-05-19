package sg.edu.nus.iss.miniproject.controllers;

import lombok.extern.slf4j.Slf4j;
import sg.edu.nus.iss.miniproject.models.SocketHandler;
import sg.edu.nus.iss.miniproject.models.ChatRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
public class ChatController extends AbstractWebSocketHandler {

    @Autowired
    private static Map<String, SocketHandler> sessionUser = new ConcurrentHashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) {
        try {
            if (session.getUri() == null) {
                log.error("Error, cannot start websocket");
                return;
            }

            final String uri = session.getUri().getPath();
            final SocketHandler socketHandler = getSocketHandler(uri);

            if (socketHandler.getUser() == null) {
                log.error("Error to get socketHandler");
                return;
            }
            socketHandler.setSession(session);
            sessionUser.put(socketHandler.getUser(), socketHandler);
            transmitMsg(socketHandler.getUser(), "login");
            transmitUsers(socketHandler.getUser());
        } catch (Throwable ex) {
            log.error("Error: ", ex);
        }
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
        try {
            if (session.getUri() == null) {
                log.error("Error, cannot start websocket");
                return;
            }

            final String uri = session.getUri().getPath();
            final SocketHandler socketHandler = getSocketHandler(uri);

            if (socketHandler == null) {
                log.error("Error to get socketHandler");
                return;
            }
            deleteSession(socketHandler.getUser());
        } catch (Throwable ex) {
            log.error("Error: ", ex);
        }
    }

    @Override
    public void handleTextMessage(final WebSocketSession session, final TextMessage message) {
        try {
            final URI uri = session.getUri();
            if (uri == null){
                log.error("URI cannot be found...");
                return;
            }
            final SocketHandler socketHandler = getSocketHandler(uri.getPath());

            if (socketHandler == null) {
                log.error("Error to get socketHandler");
                return;
            }

            try {
                ChatRequest chatRequest = objectMapper.readValue(message.getPayload(), ChatRequest.class);
                chatRequest.setFrom(socketHandler.getUser());
                sendPrivateMessage(chatRequest);
                log.debug("Chat msg sent: {}", message.getPayload());
            } catch (Exception ex) {
                log.error("Exception: ", ex);
            }
        } catch (Throwable ex) {
            log.error("Error: ", ex);
        }
    }

    public SocketHandler getSocketHandler(final String path) {
        if (path == null || path.isEmpty())
            return null;

        final String[] fields = path.split("/");

        if (fields.length == 0)
            return null;

        SocketHandler socketHandler = new SocketHandler();
        try {
            String user = fields[2];
            socketHandler.setUser(user);
        } catch (final IndexOutOfBoundsException e) {
            log.error("Can't find user & channel", e);
        }
        return socketHandler;
    }

    private void transmitMsg(String message, String type) {
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setContent(message);
        chatRequest.setDate(Instant.now().toEpochMilli());
        chatRequest.setType(type);
        for (Map.Entry<String, SocketHandler> entry : sessionUser.entrySet()) {
            try {
                entry.getValue().getSession()
                        .sendMessage(new TextMessage(objectMapper.writeValueAsString(chatRequest)));
            } catch (Exception e) {
                log.error("Exception: ", e);
            }
        }
    }
    
    private void transmitUsers(String user) {
        sendMsg("server", user, "online", String.join(",", sessionUser.keySet()));
    }

    public SocketHandler getOrDefault(String key) {
        return sessionUser.getOrDefault(key, null);
    }

    public void sendMsg(String from, String to, String type, String payload) {
        SocketHandler userTo = getOrDefault(to);
        if (userTo == null || userTo.getSession() == null) {
            log.error("user/session cannot find: {}", to);
            return;
        }
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setFrom(from);
        chatRequest.setTo(to);
        chatRequest.setDate(Instant.now().toEpochMilli());
        chatRequest.setContent(payload);
        chatRequest.setType(type);
        try {
            userTo.getSession().sendMessage(new TextMessage(objectMapper.writeValueAsString(chatRequest)));
        } catch (IOException e) {
            log.error("Exception:", e);
        }
    }

    public void deleteSession(String key) {
        SocketHandler socketHandler = getOrDefault(key);
        if (socketHandler == null || socketHandler.getSession() == null){
            log.error("cannot delete session");
            return;
        }
        sessionUser.remove(key);
        transmitUsers(socketHandler.getUser());
        transmitMsg(socketHandler.getUser(), "logout");
    }

    public void sendPrivateMessage(ChatRequest chatRequest) {
        SocketHandler userTo = getOrDefault(chatRequest.getTo());
        if (userTo == null || userTo.getSession() == null) {
            log.error("user/session cannot find: {}", chatRequest.getTo());
            return;
        }
        chatRequest.setType("private");
        chatRequest.setDate(Instant.now().toEpochMilli());
        try {
            userTo.getSession().sendMessage(new TextMessage(objectMapper.writeValueAsString(chatRequest)));
        } catch (IOException e) {
            log.error("Exception: ", e);
        }
    }
}