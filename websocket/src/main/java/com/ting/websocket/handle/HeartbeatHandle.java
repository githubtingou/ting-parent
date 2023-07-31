package com.ting.websocket.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 心跳处理
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/3
 */
@Component
@Slf4j
public class HeartbeatHandle {

    @Scheduled(cron = "/10 * * * * ? *")
    public void handle(){
        log.info("处理心跳");

    }
}
