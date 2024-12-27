package org.qiranlw.easygo.config;

import org.qiranlw.easygo.exception.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author qiranlw
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.exceptionHandling(customizer -> customizer.accessDeniedHandler(new CustomAccessDeniedHandler()));

        httpSecurity.httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(formLogin -> {
                    formLogin.loginPage("/login.html")
                            .loginProcessingUrl("/login")
                            .failureUrl("/login.html?error")
                            .defaultSuccessUrl("/index.html");
                })
                .logout(logout -> {
                    logout.deleteCookies("remove")
                            .invalidateHttpSession(false)
                            .logoutSuccessUrl("/login.html");
                });
        return httpSecurity.build();
    }
}
