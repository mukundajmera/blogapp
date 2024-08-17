package com.example.blogapp.security;

import com.example.blogapp.users.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class AppSecurityConfig {

    private JWTAuthenticationFilter jwtAuthenticationFilter;
    private JWTService jwtService;
    private UsersService usersService;


    public AppSecurityConfig(JWTService jwtService, UsersService usersService) {
        this.jwtService = jwtService;
        this.usersService = usersService;
        this.jwtAuthenticationFilter = new JWTAuthenticationFilter(new JWTAuthenticationManager(this.jwtService, this.usersService));
    }

    @Bean
    JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter(
                new JWTAuthenticationManager(jwtService, usersService));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET, "/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/articles", "/articles/*").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }
}
