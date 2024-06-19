package org.delivery.storeadmin.config.security;

import org.delivery.db.storeuser.enums.StoreUserRole;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/error");
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http
            .authorizeHttpRequests(it->{
                it
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .requestMatchers(SWAGGER.toArray(new String[0])).permitAll()
                    .requestMatchers("/open-api/**").permitAll()
                    .requestMatchers("/store-user/**").permitAll()
                    .requestMatchers("/store-order/**").hasRole(StoreUserRole.ADMIN.toString())
                    .requestMatchers("/store-menu/**").hasRole(StoreUserRole.ADMIN.toString())
                    .requestMatchers("/master/**").hasRole(StoreUserRole.MASTER.toString())
                    .anyRequest().authenticated();
            });

        http
            .formLogin(form->
                    form
                    .loginPage("/store-user/login").permitAll()
                    .defaultSuccessUrl("/")
                    .failureUrl("/store-user/login/error")
            );
        http
            .logout(it->
                    it.logoutSuccessUrl("/store-user/login")
            );

        http
            .exceptionHandling(
                    it->it.accessDeniedPage("/store-user/error/forbidden")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}