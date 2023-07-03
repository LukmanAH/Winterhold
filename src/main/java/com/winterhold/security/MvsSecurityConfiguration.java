package com.winterhold.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MvsSecurityConfiguration {

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((request) ->request
                .requestMatchers("/resources/**", "/account/**").permitAll()
                .anyRequest().authenticated()
        ).formLogin((form)-> form
                .loginPage("/account/loginForm") //redirect ketika butuh akses login
                .loginProcessingUrl("/submitLogin") //url ke action post login
        ).logout((logout)->logout.permitAll()
        ).exceptionHandling((handler) -> handler.accessDeniedPage("/account/accessDenied"));

        return http.build();
    }

}
