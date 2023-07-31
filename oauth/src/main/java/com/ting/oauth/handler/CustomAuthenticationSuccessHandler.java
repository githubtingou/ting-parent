package com.ting.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.ting.common.domain.R;

import java.io.IOException;

/**
 * 成功
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/6
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        R<Authentication> success = R.success(authentication);
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(success);
        response.getWriter().write(value);
    }

}
