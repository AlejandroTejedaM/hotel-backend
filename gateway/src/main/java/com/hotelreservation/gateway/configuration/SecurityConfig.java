package com.hotelreservation.gateway.configuration;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf(CsrfSpec::disable)
                .cors(corsSpec -> corsSpec.configurationSource(exchange -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // --------------- RESERVACIONES ------------
                        .pathMatchers(HttpMethod.GET,    "/api/reservaciones/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers(HttpMethod.PUT,    "/api/reservaciones/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers(HttpMethod.POST,   "/api/reservaciones/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers(HttpMethod.DELETE, "/api/reservaciones/**").hasAnyRole("ADMIN", "USER")

                        // --------------- HABITACIONES ------------
                        .pathMatchers(HttpMethod.GET,    "/api/habitaciones/**").hasAnyRole("ADMIN", "USER")
                        // TODO: clarificar con profe si USER puede modificar tipo, numero, capacidad
                        .pathMatchers(HttpMethod.PUT,    "/api/habitaciones/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers(HttpMethod.POST,   "/api/habitaciones/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/api/habitaciones/**").hasRole("ADMIN")

                        // --------------- HUESPEDES ------------
                        .pathMatchers(HttpMethod.GET,    "/api/huespedes/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers(HttpMethod.POST,   "/api/huespedes/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers(HttpMethod.PUT,    "/api/huespedes/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers(HttpMethod.DELETE, "/api/huespedes/**").hasAnyRole("ADMIN", "USER")
                        .anyExchange().authenticated()

                )

                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt ->
                        jwt.jwtAuthenticationConverter(reactiveJwtAuthenticationConverterAdapter())));
        return http.build();
    }

    @Bean
    ReactiveJwtAuthenticationConverterAdapter reactiveJwtAuthenticationConverterAdapter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        grantedAuthoritiesConverter.setAuthorityPrefix("");// ROL_ -> ROLE_ADMIN

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
