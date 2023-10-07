package com.tokyovending.TokyoVending.config;

import com.tokyovending.TokyoVending.filter.JwtRequestFilter;
import com.tokyovending.TokyoVending.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    // Authorizatie met jwt
    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                // Wanneer je deze uncomments, staat je hele security open. Je hebt dan alleen nog een jwt nodig.
//                .requestMatchers("/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.GET,"/users").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET,"/users/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/users/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT,"/users/{username}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/users/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/users/download-order-history/{username}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/users/export/all-orders").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/vending-machines").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/vending-machines").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/vending-machines/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/vending-machines/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/vending-machines/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/vending-machines/{id}/add-product/{productId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/vending-machines/{id}/remove-product/{productId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/vending-machines/{id}/change-status").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/categories").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/categories").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/categories/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/categories/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/categories/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/categories/{id}/add-product/{productId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/categories/{id}/remove-product/{productId}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/products").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/products/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/products/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/products/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/carts").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/carts").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/carts/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/carts/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/carts/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/carts/{id}/add-product/{productId}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/carts/{id}/remove-product/{productId}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/carts/{id}/convert-to-order").hasAnyRole("ADMIN", "USER")

                .requestMatchers(HttpMethod.POST, "/orders").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/orders").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/orders/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/orders/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/orders/{id}").hasAnyRole("ADMIN", "USER")

                .requestMatchers(HttpMethod.POST, "/files/upload-profile-pic/{username}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/files/delete-profile-pic/{username}").hasAnyRole("ADMIN", "USER")

                // Je mag meerdere paths tegelijk definieren

                .requestMatchers("/authenticated").authenticated()
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
