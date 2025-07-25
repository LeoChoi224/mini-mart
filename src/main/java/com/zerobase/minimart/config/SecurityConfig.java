package com.zerobase.minimart.config;

import com.zerobase.minimart.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserServiceImpl userServiceImpl;
    private final PasswordEncoder passwordEncoder;

    // 커스텀 로그인 성공 핸들러 주입
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    // 로그인 실패 핸들러
    @Bean
    public UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    // 접근 거부 핸들러 (판매자 여부 검사 실패 시)
    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }


    // 보안 필터 체인
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // 일반 허용 경로
                        .requestMatchers(
                                "/", "/user/**", "/user/signup/**", "/order/common/**",  "/css/**", "/js/**", "/images/**"
                        ).permitAll()

                        // 판매자만 접근 허용
                        .requestMatchers("/order/seller/**")
                        .hasRole("SELLER")

                        // 구매자만 접근 허용
                        .requestMatchers("/order/customer/**")
                        .hasRole("CUSTOMER")

                        // 그 외는 인증 필요
                        .anyRequest().authenticated()
                )
                // 로그인 설정
                .formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/user/login")
                        .usernameParameter("userId")   // "username" 대신 "userId"로 처리
                        .passwordParameter("password")
                        .successHandler(loginSuccessHandler)
                        .failureHandler(getFailureHandler())
                        .permitAll()
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                )
                // 접근 권한 실패 시 커스텀 핸들러 적용
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(customAccessDeniedHandler())
                )
                .addFilterBefore(new LoginSuccessFilter(), UsernamePasswordAuthenticationFilter.class)
                // CSRF, CORS는 비활성화 (개발용)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
        ;

        return http.build();
    }

    // 인증 관리자 등록 (UserDetailsService + passwordEncoder 연결)
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder
                .userDetailsService(userServiceImpl)
                .passwordEncoder(passwordEncoder);
        return builder.build();
    }
}