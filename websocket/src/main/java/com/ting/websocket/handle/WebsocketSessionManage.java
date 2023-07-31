package com.ting.websocket.handle;

import com.ting.websocket.request.WebSocketMessageRequest;
import com.ting.websocket.request.WebsocketRequestParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket session管理
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/3
 */
@Slf4j
public final class WebsocketSessionManage {
    private static final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>(100);
    private static final Map<String, WebsocketRequestParam> sessionParamMap = new ConcurrentHashMap<>(100);


    /**
     * 新增session
     *
     * @param key
     * @param session
     */
    public static void addSession(String key, WebSocketSession session) {
        sessionMap.put(key, session);
    }

    /**
     * 关闭session
     *
     * @param key
     * @param session
     */
    public static void removeAndCloseSession(String key, WebSocketSession session) {
        sessionMap.remove(key, session);
        try {
            session.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 消息发送
     *
     * @param session
     * @param message
     * @throws IOException
     */
    public static void sendMessage(WebSocketSession session, WebSocketMessageRequest<?> message) throws IOException {
        session.sendMessage(new TextMessage(message.toString()));
    }

    /**
     * 根据id推送消息
     *
     * @param sessionId
     * @param message
     * @throws IOException
     */
    public static void pushMessageById(String sessionId, WebSocketMessageRequest<?> message) throws IOException {
        WebSocketSession session = sessionMap.get(sessionId);
        if (session == null || !session.isOpen()) {
            log.info("没有该[{}]的websocket", sessionId);
            return;
        }
        session.sendMessage(new TextMessage(message.toString()));
    }

    /**
     * 根据id推送消息
     *
     * @param sessionIds
     * @param message
     * @throws IOException
     */
    public static void pushMessageById(Set<String> sessionIds, WebSocketMessageRequest<?> message) throws IOException {

        // 获取所有session
        List<WebSocketSession> sessionList = new ArrayList<>();
        for (String sessionId : sessionIds) {
            WebSocketSession session = sessionMap.get(sessionId);
            if (session == null) {
                log.info("没有该[{}]的websocket", sessionId);

            } else {
                sessionList.add(session);
            }
        }

        // 消息发送
        for (WebSocketSession session : sessionList) {
            session.sendMessage(new TextMessage(message.toString()));
        }
    }

}
