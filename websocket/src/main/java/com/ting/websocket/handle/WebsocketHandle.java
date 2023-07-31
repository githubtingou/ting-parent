package com.ting.websocket.handle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * websocket处理器
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/3
 */
@Configuration
@Slf4j
public class WebsocketHandle extends TextWebSocketHandler {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{}建立链接:{}", session.getRemoteAddress(), session.getUri());
        URI uri = session.getUri();
        MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUri(uri).build().getQueryParams();

        if (CollectionUtils.isEmpty(queryParams)) {
            log.warn("参数不能为空");
            WebSocketMessage<String> message = new TextMessage("参数不对无法发送消息");
            session.sendMessage(message);
            session.close();
            return;
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap = queryParams.toSingleValueMap();
        WebsocketSessionManage.addSession(session.getId(), session);

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("消息处理:{},{}", session.getId(), message);
        Object payload = message.getPayload();
        WebSocketMessage<?> webSocketMessage = objectMapper.readValue(payload.toString(), new TypeReference<WebSocketMessage>() {
        });
        System.out.println(webSocketMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.info("异常处理:{},{}", session.getId(), exception);
        WebsocketSessionManage.removeAndCloseSession(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("websocket关闭:{}-{}", session.getId(), closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
