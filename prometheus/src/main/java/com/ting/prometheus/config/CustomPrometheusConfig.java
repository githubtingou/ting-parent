package com.ting.prometheus.config;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 *
 * @author ting
 * @version 1.0
 * @date 2023/6/29
 */
@Configuration
@Slf4j
public class CustomPrometheusConfig {
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName) {
        return registry -> registry.config()
                .commonTags("application", applicationName)
                .onMeterRegistrationFailed((id, s) -> log.error("id为[{}]执行失败,错误信息:{}", id, s));
    }

}
