package com.ting.websocket.request;

import lombok.Data;

/**
 * websocket请求消息
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/3
 */
@Data
public class WebsocketRequestParam {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户部门
     */
    private String dept;
}
