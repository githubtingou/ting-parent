package com.ting.websocket.request;

import lombok.Data;

import java.util.List;

/**
 * websocket消息
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/3
 */
@Data
public class WebSocketMessageRequest<T> {
    /**
     * 消息类型
     */
    private int type;

    /**
     * 接受用户
     */
    private List<String> acceptUsers;

    /**
     * 消息来源
     */
    private String fromUser;

    /**
     * 消息发送端
     */
    private int sendClient;

    /**
     * 消息主题
     */
    private T data;



}
