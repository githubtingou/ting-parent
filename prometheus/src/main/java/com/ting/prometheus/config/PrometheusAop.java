package com.ting.prometheus.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

/**
 * @author ting
 * @version 1.0
 * @date 2023/6/29
 */
@Aspect
@Configuration
public class PrometheusAop {

    @Pointcut(value = "@annotation(PrometheusAnno)")
    public void pointcut() {

    }

    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MeterRegistry registry;


    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint point) {
        Object proceed = null;

        // 封装参数
        Object[] args = point.getArgs();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Map<String, ?> parameterMap = new HashMap<>();
        // post请求且入参格式是json格式
        if (RequestMethod.POST.toString().equals(request.getMethod())
                && (MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType()) || MediaType.APPLICATION_JSON_UTF8_VALUE.equals(request.getContentType()))) {
            HashMap<String, String> hashMap = new HashMap<>();
            try {
                String value = mapper.writeValueAsString(args[0]);
                hashMap = mapper.readValue(value, new TypeReference<HashMap<String, String>>() {
                });

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            parameterMap = hashMap;
        } else {
            parameterMap = request.getParameterMap();
        }

        // 获取切面的值
        MethodSignature signature = (MethodSignature) point.getSignature();
        PrometheusAnno annotation = signature.getMethod().getAnnotation(PrometheusAnno.class);
        String[] paramKey = annotation.paramKey();
        String[] paramValue = annotation.paramValue();
        List<String> tags = new ArrayList<>();
        // 封装tags
        if (annotation.tags().length != 0 && annotation.tags().length % 2 == 0) {
            tags.addAll(Arrays.asList(annotation.tags()));
        }
        // 封装自定义的参数
        if (paramKey.length != 0 && paramKey.length == paramValue.length) {
            for (int i = 0; i < paramValue.length; i++) {
                String value = null;
                String key = paramValue[i];
                if (parameterMap.get(key) instanceof String[]) {
                    String[] strings = (String[]) parameterMap.get(key);
                    value = strings[0];
                } else {
                    value = String.valueOf(parameterMap.get(key));
                }

                tags.add(paramKey[i]);
                tags.add(value);
            }

        }
        try {
            proceed = point.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        if (!CollectionUtils.isEmpty(tags)) {
            this.registry.counter(annotation.name(), tags.toArray(new String[0]))
                    .increment();
        }

        return proceed;
    }
}
