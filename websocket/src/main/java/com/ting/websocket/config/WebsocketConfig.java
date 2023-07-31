package com.ting.websocket.config;

import com.ting.websocket.handle.WebsocketHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * websocket配置
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/3
 */
@EnableConfigurationProperties(value = WebsocketProperties.class)
@Configuration
@ConditionalOnClass(WebsocketProperties.class)
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {
    @Autowired
    private WebsocketProperties properties;
    @Autowired
    private WebsocketHandle websocketHandle;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(websocketHandle, properties.getPath());
    }
}
