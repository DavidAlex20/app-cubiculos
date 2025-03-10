package com.um.appasistencias.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Autowired private ReactiveUserDetailsService userDetailsService;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeExchange(exchange -> exchange
                .pathMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/assets/**").permitAll()
                .pathMatchers("/login", "/register", "/logout").permitAll()
                .pathMatchers("/admin/**", "/auth/**").hasAuthority("ADMIN")
                .anyExchange().authenticated()
                )
            .httpBasic(withDefaults())
            .formLogin(login -> login
                .loginPage("/login")
                .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/dashboard"))
                .authenticationFailureHandler(new RedirectServerAuthenticationFailureHandler("/login?error=true"))
                )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler(new RedirectServerLogoutSuccessHandler())
                )
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager auth =
            new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
}
