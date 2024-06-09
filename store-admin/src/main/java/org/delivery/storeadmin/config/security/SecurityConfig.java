package org.delivery.storeadmin.config.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/store-user/**"
    );
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
//                .formLogin(Customizer.withDefaults())
        ;

        http
            .authorizeHttpRequests(it->{
                it
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .requestMatchers(SWAGGER.toArray(new String[0])).permitAll()
                    .requestMatchers("/open-api/**").permitAll()
                    .anyRequest().authenticated();
            });

        http
            .formLogin(form->
                    form
                    .loginPage("/store-user/login").permitAll()
                    .defaultSuccessUrl("/main")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
