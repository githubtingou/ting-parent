package com.ting.websocket.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息类型
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/3
 */
@Getter
@AllArgsConstructor
public enum MessageTypeEnum {

    Normal(0, "普通消息"),
    ;

    private final int code;
    private final String codeName;

}
