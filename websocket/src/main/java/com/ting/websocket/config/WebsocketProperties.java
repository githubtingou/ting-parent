package com.ting.websocket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * websocket配置
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/3
 */
@ConfigurationProperties(prefix = WebsocketProperties.WEBSOCKET_PREFIX)
@Data
public class WebsocketProperties {
    public static final String WEBSOCKET_PREFIX = "websocket";

    /**
     * 地址
     */
    private String path = "/ws";

}
