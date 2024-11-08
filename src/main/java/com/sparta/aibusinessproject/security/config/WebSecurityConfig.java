package com.sparta.aibusinessproject.security.config;

import com.sparta.aibusinessproject.security.jwt.JwtConfig;
import com.sparta.aibusinessproject.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CorsFilter corsFilter;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable())
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                .permitAll() // resources 접근 허용 설정
                                .requestMatchers("/auth/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
                                .anyRequest().authenticated() // 그 외 모든 요청 인증처리
                )
                .with(new JwtConfig(jwtUtil), customizer -> customizer.getClass());


        return http.build();
    }
}