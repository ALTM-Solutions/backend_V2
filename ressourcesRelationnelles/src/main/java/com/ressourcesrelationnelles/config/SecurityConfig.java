package com.ressourcesrelationnelles.config;

import com.ressourcesrelationnelles.model.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;
    // Avant de passer par le controller, la requête HTTP passe par ce code
    // Filtre les entrées HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers("/api/citoyens/**").hasAnyAuthority(UserType.CITIZEN.toString(),UserType.ADMIN.toString(),UserType.SUPER_ADMIN.toString(),UserType.MODERATEUR.toString())
                .requestMatchers("/api/admin/**").hasAnyAuthority(UserType.ADMIN.toString(),UserType.SUPER_ADMIN.toString())
                .requestMatchers("/api/moderateur/**").hasAnyAuthority(UserType.MODERATEUR.toString(),UserType.ADMIN.toString(),UserType.SUPER_ADMIN.toString())
                .requestMatchers("/api/super-admin/**").hasAnyAuthority(UserType.SUPER_ADMIN.toString())
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/api-docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
//        http
//                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//                .cors(cors -> {})
//                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthEntryPoint))
//                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers("/api/citoyens/**").hasAnyAuthority(UserType.CITIZEN.toString(),UserType.ADMIN.toString(),UserType.SUPER_ADMIN.toString(),UserType.MODERATEUR.toString())
//                        .requestMatchers("/api/admin/**").hasAnyAuthority(UserType.ADMIN.toString(),UserType.SUPER_ADMIN.toString())
//                        .requestMatchers("/api/moderateur/**").hasAnyAuthority(UserType.MODERATEUR.toString(),UserType.ADMIN.toString(),UserType.SUPER_ADMIN.toString())
//                        .requestMatchers("/api/super-admin/**").hasAnyAuthority(UserType.SUPER_ADMIN.toString())
//                        .requestMatchers("/api/public/**").permitAll()
//                        .requestMatchers("/swagger-ui/**").permitAll()
//                        .requestMatchers("/api-docs/**").permitAll()
//                        .anyRequest().authenticated())
//                .httpBasic(httpBasic -> {});
//        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Utilisation de BCRYPT pour le hachage du MDP
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Utilisation de JWT pour gérer l'authentification et l'autorisation
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
}