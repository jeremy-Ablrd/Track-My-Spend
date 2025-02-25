package com.trackmyspend.trackmyspend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.trackmyspend.trackmyspend.Service.CustomUserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // Active les annotations de sécurité
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // gestion du jwt
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Désactive CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/user/create").permitAll() // Public
                .requestMatchers("/admin/**").hasRole("ADMIN") // Routes accessibles uniquement aux ADMIN
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // Accessible aux USER et ADMIN
                .anyRequest().authenticated() // Tout le reste est protégé
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Pas de session
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ######## Auth Basic ########
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf(csrf -> csrf.disable()) // Désactive CSRF pour simplifier (attention en prod !)
    //         .authorizeHttpRequests(auth -> auth
    //             .requestMatchers("/login", "/playersTest/players/create").permitAll() // Permet l'accès à /login sans authentification
    //             .anyRequest().authenticated() // Nécessite une authentification pour tout le reste
    //         )
    //         .exceptionHandling(exception -> exception
    //             .authenticationEntryPoint((request, response, authException) -> {
    //                 // Retourne un 401 Unauthorized au lieu d'un 403 Forbidden
    //                 response.setStatus(401);
    //                 response.getWriter().write("Unauthorized - Please login");
    //             })
    //         )
    //         .httpBasic(Customizer.withDefaults());
    //         // .httpBasic(httpBasic -> httpBasic.init(http)); // Active l'authentification HTTP basique

    //     return http.build();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Utilise BCrypt pour encoder les mots de passe
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(customUserDetailsService)
               .passwordEncoder(passwordEncoder);
        return builder.build();
    }
}