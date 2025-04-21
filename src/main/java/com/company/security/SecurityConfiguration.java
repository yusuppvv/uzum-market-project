package com.company.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Bean//UserRepository  UserDAO
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                {
                    auth

                            .requestMatchers("/api/v1/auth/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/products").hasAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.POST, "/api/products").hasAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/products/get/*").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/api/products/delete/*").hasAuthority("ROLE_ADMIN")
                            .requestMatchers("/api/v1/students/register").permitAll()
                            .requestMatchers("/api/v1/students/verify").permitAll()
                            .requestMatchers("/api/v1/students/login").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/products/update/*").hasAuthority("ROLE_ADMIN")
                            .anyRequest()
                            .authenticated();
                })
                .formLogin(AbstractHttpConfigurer::disable)
//                .httpBasic(Customizer.withDefaults())
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> { // cors konfiguratsiya qilingan
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
                    configuration.setAllowedMethods(Arrays.asList("*"));
                    configuration.setAllowedHeaders(Arrays.asList("*"));

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    httpSecurityCorsConfigurer.configurationSource(source);
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
