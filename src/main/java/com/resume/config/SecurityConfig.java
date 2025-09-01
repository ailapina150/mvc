package com.resume.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

//    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
//    private String JWK_SET_URI;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/promo-cod/**").hasRole("ADMIN")
//                        .requestMatchers("/**").permitAll()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt
//                                .decoder(jwtDecoder())
//                                .jwtAuthenticationConverter(customJwtAuthenticationConverter())
//                        )
//                )
//                .csrf(AbstractHttpConfigurer::disable)  // ← ПОЛНОСТЬЮ ОТКЛЮЧАЕМ CSRF
//                .exceptionHandling(handling -> handling
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            System.out.println("Access denied for: " + request.getRequestURI() + " " + request.getMethod());
//                            response.setStatus(HttpStatus.FORBIDDEN.value());
//                            response.getWriter().write("Access denied for: " + request.getMethod() + " " + request.getRequestURI());
//                        })
//                        .authenticationEntryPoint((request, response, authException) -> {
//                            System.out.println("Authentication required for: " + request.getRequestURI() + " " + request.getMethod());
//                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                            response.getWriter().write("Authentication required for: " + request.getMethod() + " " + request.getRequestURI());
//                        })
//                )
//                .build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOriginPatterns(List.of(
//                "http://localhost:5444",
//                "http://localhost:8081",
//                "http://localhost:8000",
//                "http://localhost:5672",
//                "http://localhost:6379",
//                "http://localhost:5433",
//                "http://localhost:9200",
//                "http://localhost:5601",
//                "http://127.0.0.1:5444",
//                "http://127.0.0.1:8081",
//                "http://127.0.0.1:8000",
//                "http://127.0.0.1:5672",
//                "http://127.0.0.1:6379",
//                "http://127.0.0.1:5433",
//                "http://127.0.0.1:9200",
//                "http://127.0.0.1:5601"
//        ));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With"));
//        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
//        configuration.setAllowCredentials(true);
//        configuration.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return NimbusJwtDecoder.withJwkSetUri(JWK_SET_URI).build();
//    }
//
//    @Bean
//    public Converter<Jwt, AbstractAuthenticationToken> customJwtAuthenticationConverter() {
//        return new AuthenticationConverter();
//    }
}
