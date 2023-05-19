package sg.edu.nus.iss.miniproject.config;

import sg.edu.nus.iss.miniproject.controllers.ChatController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

	@Autowired
    private ChatController chatController;

    @Override
    public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry) {
        registry.addHandler(chatController, "/websocket/{userId}")
                .setAllowedOrigins("*");
    }
}