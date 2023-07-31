package com.ting.oauth.controller;

import com.ting.oauth.domain.LoginUser;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.ting.common.domain.R;

/**
 * 用户登陆
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/6
 */
@RestController
@RequiredArgsConstructor
public class LoginController {

    private AuthenticationManager manager;
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/doLogin")
    public R<Void> doLogin(@RequestBody LoginUser loginUser, HttpSession session) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(loginUser.getUsername(), loginUser.getPassword());
        Authentication authenticate = manager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,session);
        return R.success();
    }

}
