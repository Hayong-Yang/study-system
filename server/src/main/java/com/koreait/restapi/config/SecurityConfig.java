package com.koreait.restapi.config;

import com.koreait.restapi.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfig loaded");
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/register", "/api/member/register", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                                .loginPage("/login")  // 프론트에서 커스텀 페이지 보여주기용 경로
                                .loginProcessingUrl("/login") // 요청은 여기에 보내짐
//                                .loginProcessingUrl("/api/member/login") // ✅ 프론트와 일치
                                .successHandler((request, response, authentication) -> {
                                    System.out.println("로그인 성공: " + authentication.getName());
                                    response.setStatus(HttpServletResponse.SC_OK);
                                    response.getWriter().write("로그인 성공");
                                })
                                .failureHandler((request, response, exception) -> {
                                    System.out.println("로그인 실패: " + exception.getMessage());
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                    response.getWriter().write("로그인 실패");
                                })
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // <- POST 요청 경로
                        .logoutSuccessUrl("/") // <- 성공 시 리다이렉트 경로
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

} // class
