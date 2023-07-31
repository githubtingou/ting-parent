package com.ting.prometheus.config;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author ting
 * @version 1.0
 * @date 2023/6/29
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface PrometheusAnno {

    /**
     * tags 必须成双出现
     * <p>
     * Prometheus是以key value取值的
     *
     * @return
     */
    @AliasFor(value = "tags")
    String[] value() default {};

    @AliasFor(value = "value")
    String[] tags() default {};

    /**
     * 唯一标识,不能重复
     *
     * @return
     */
    String name();

    /**
     * 入参中的字段,别名
     *
     * @return
     */
    String[] paramKey() default {};

    /**
     * 入参得value
     * @return
     */
    String[] paramValue() default {};

}
