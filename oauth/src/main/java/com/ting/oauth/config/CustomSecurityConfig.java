package com.ting.oauth.config;

import com.ting.oauth.handler.CustomAuthenticationFailureHandler;
import com.ting.oauth.handler.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * security配置
 *
 * @author ting
 * @version 1.0
 * @date 2023/7/6
 */
@Configuration
@EnableWebSecurity(debug = true)
public class CustomSecurityConfig {

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                        matcherRegistry ->
                                matcherRegistry.requestMatchers("/doLogin", "/logout").permitAll()
                                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin.failureHandler(new CustomAuthenticationFailureHandler())
                        .successHandler(new CustomAuthenticationSuccessHandler()))
                .csrf(AbstractHttpConfigurer::disable)
        ;

        return httpSecurity.build();
    }

    /**
     * 检测用户
     *
     * @return
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        UserDetails build = User.withUsername("test").password("$2a$10$RqpHsjoejxv22x6iiHfQ1uW6umgjpIF38.VPDcjh208KZpUFekWg6").roles("ADMIN").build();
        manager.createUser(build);
        provider.setUserDetailsService(manager);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);

    }

    /**
     * 密码
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public JwtAuthenticationToken tokenService(){
//
//    }

}
