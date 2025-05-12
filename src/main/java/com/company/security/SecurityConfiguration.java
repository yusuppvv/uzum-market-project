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
import java.util.List;

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
                            //Cart controller
                            .requestMatchers(HttpMethod.POST, "/api/v1/cart").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/cart/get-by-id/*").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/cart/get-all").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/cart/get-by-user/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_MODERATOR")
                            .requestMatchers(HttpMethod.PUT, "/api/v1/cart/*").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/cart/delete/*").permitAll()

                            //Category controller
                            .requestMatchers(HttpMethod.POST, "/api/v1/category").hasAnyAuthority("ROLE_ADMIN", "ROLE_MODERATOR")
                            .requestMatchers(HttpMethod.GET, "/api/v1/category/get-by-id/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_MODERATOR")
                            .requestMatchers(HttpMethod.GET, "/api/v1/category/get-all").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/v1/category/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_MODERATOR")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/category/delete/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_MODERATOR")

                            //Order controller
                            .requestMatchers(HttpMethod.POST, "/api/v1/orders").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/orders/get-by-id/*").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/orders/get-all").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/orders/get-by-user/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_MODERATOR")
                            .requestMatchers(HttpMethod.PATCH, "/api/v1/orders/pay").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/v1/orders/*").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/delete/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_MODERATOR")

                            //Photo controller
                            .requestMatchers(HttpMethod.POST, "/api/v1/photo/upload").hasAuthority("ROLE_SELLER")
                            .requestMatchers(HttpMethod.POST, "/api/v1/photo/multi-upload").hasAuthority("ROLE_SELLER")
                            .requestMatchers(HttpMethod.GET, "/api/v1/photo/metadata/*").hasAuthority("ROLE_SELLER")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/photo/delete-by-id/*").hasAuthority("ROLE_SELLER")
                            .requestMatchers(HttpMethod.GET, "/api/v1/photo/get-photo-by-id/*").hasAuthority("ROLE_SELLER")
                            .requestMatchers(HttpMethod.GET, "/api/v1/photo/get-by-product-id/*").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/photo/search").permitAll()

                            //Product controller
                            .requestMatchers(HttpMethod.POST, "/api/v1/product/create-product").hasAuthority("ROLE_SELLER")
                            .requestMatchers(HttpMethod.GET, "/api/v1/product/get-by-id/*").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/product/get-all").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/product/category/*").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/product/seller/*").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/product/price-range").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/v1/product/update/*").hasAuthority("ROLE_SELLER")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/product/delete/*").hasAnyAuthority("ROLE_SELLER", "ROLE_ADMIN", "ROLE_MODERATOR")

                            //Review controller
                            .requestMatchers(HttpMethod.POST, "/api/v1/reviews/create").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/reviews/get-by-id/*").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/reviews/product/*").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/reviews/user/*").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/v1/reviews/update").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/reviews/delete/*").permitAll()

                            //User controller
                            .requestMatchers(HttpMethod.POST, "/api/v1/user/create").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/user/get-all").hasAnyAuthority("ROLE_ADMIN", "ROLE_MODERATOR")
                            .requestMatchers(HttpMethod.GET, "/api/v1/user/get-by-id/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_MODERATOR")
                            .requestMatchers(HttpMethod.PUT, "/api/v1/user/update/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_MODERATOR")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/user/delete/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_MODERATOR")

                            //Auth
                            .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/auth/verification").permitAll()
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
                    configuration.setAllowedOriginPatterns(List.of("*"));
                    configuration.setAllowedMethods(List.of("*"));
                    configuration.setAllowedHeaders(List.of("*"));

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    httpSecurityCorsConfigurer.configurationSource(source);
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
