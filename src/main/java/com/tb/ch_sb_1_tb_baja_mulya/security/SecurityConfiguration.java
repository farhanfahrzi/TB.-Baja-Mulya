package com.tb.ch_sb_1_tb_baja_mulya.security;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final AuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
//						.httpBasic(new Customizer<HttpBasicConfigurer<HttpSecurity>>() {
//							@Override
//							public void customize(HttpBasicConfigurer<HttpSecurity> httpSecurityHttpBasicConfigurer) {
//
//							}
//						})// ini belum jadi lamda
//						.httpBasic(security -> {
//							security.disable();
//						})// ini sudah lamda, tapi masih ada ada yg lebih singkat, yaitu method refrence.
                .httpBasic(AbstractHttpConfigurer::disable)
                // disable csrf
                .csrf(AbstractHttpConfigurer::disable)
                // disable cookie
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // kita berikan authenticate untuk semua enpoint
                .authorizeHttpRequests(req -> req
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/products/").permitAll()
                        .anyRequest().authenticated()
                )
                // menambahakn filter, dari Authentication filter
//						.addFilterBefore() // belum ada
                .addFilterBefore(
                        authenticationFilter, // ini filter kita
                        UsernamePasswordAuthenticationFilter.class // filternya berdasakan apa?
                )
                .build();
    }
}

