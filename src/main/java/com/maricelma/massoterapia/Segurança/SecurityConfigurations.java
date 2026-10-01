package com.maricelma.massoterapia.Segurança;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;

    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception{
        return http
        .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req ->{
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/login/cadastrar").permitAll();

                    req.requestMatchers("/financeiro/**").hasAnyRole("ADMIN", "PROFISSIONAL");
                    req.requestMatchers("/bloqueios/**").hasAnyRole("ADMIN", "PROFISSIONAL");

                    req.requestMatchers(HttpMethod.POST, "/servicos/**").hasAnyRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/servicos/**").hasAnyRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/servicos/**").hasAnyRole("ADMIN");
                    req.requestMatchers(HttpMethod.POST, "/horarios/**").hasAnyRole("ADMIN");

                    req.requestMatchers(HttpMethod.GET, "/servicos/**").permitAll();

                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception{
        return  configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
