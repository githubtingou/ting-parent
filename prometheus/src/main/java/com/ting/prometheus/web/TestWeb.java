package com.ting.prometheus.web;

import com.ting.prometheus.config.PrometheusAnno;
import com.ting.prometheus.request.TestRequest;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ting
 * @version 1.0
 * @date 2023/6/29
 */
@RestController
@RequestMapping(value = "/test")
public class TestWeb {


    @Autowired
    private MeterRegistry registry;
    Counter counter;


    /**
     * post_param_total{application="ting-prometheus", instance="ting-prometheus",post_key_tag="post-form"}
     * @param name
     * @return
     */
    @PostMapping(value = "/param")
    @PrometheusAnno(name = "post_param_total", paramKey = "post_key_tag", paramValue = "name")
    public String test(@RequestParam(value = "name") String name) {

        return name;

    }

    /**
     * ting_total{application="ting-prometheus", instance="ting-prometheus",post_param_key_tag="post-json"}
     * @param request
     * @return
     */
    @PostMapping(value = "/post")
    @PrometheusAnno(name = "get_total", paramKey = "post_param_key_tag", paramValue = "name")
    public String param(@RequestBody TestRequest request) {
        return request.getName();

    }

    @GetMapping(value = "/get")
    @PrometheusAnno(name = "get_total", tags = {"tag_test","AA"},paramKey = "get_key_tag", paramValue = "name")
    public String get(@RequestParam(value = "name") String name) {
        return name;
    }
}
