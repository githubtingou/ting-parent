package com.ting.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.ting.common.domain.R;
import org.ting.common.domain.REnum;

import java.io.IOException;

/**
 * 自定义登录成功
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/6
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        R<Void> fail = R.fail(REnum.AUTH_FAIL);
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(fail);
        response.getWriter().write(value);

    }
}
