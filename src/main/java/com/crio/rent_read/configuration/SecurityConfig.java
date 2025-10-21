package com.crio.rent_read.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Import HttpMethod
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // This enables @PreAuthorize annotations
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Chain all rules in a single lambda for readability
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
         // Rule #1: The front door for signing up or logging in is unlocked.
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll() 
                        // Make registration and login endpoints public
                        
                        // Allow anyone to browse books
                         // Rule #2: Anyone is allowed to look at the list of books.
                        .requestMatchers(HttpMethod.GET, "/books").permitAll()
                        // Require authentication for any other request
                        .anyRequest().authenticated() 
                )
                // And the way we check if you're "logged in" is with a username and password.
                .httpBasic(Customizer.withDefaults())
                .build();
    }
    
    @Bean // Expose AuthenticationManager as a Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // UNCOMMENT AND DEFINE THIS BEAN
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    // UNCOMMENT AND DEFINE THIS BEAN
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // Set the "ID Checker"
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        // Set the Password Encoder
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

   /* @Bean
    public UserDetailsService userDetailsService() {
        // We'll define the implementation for this in another class
        return new UserDetailsService();
    }

   

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This bean is removed because with @PreAuthorize, it's often clearer to keep the ROLE_ prefix,
    // e.g., @PreAuthorize("hasRole('ADMIN')"). This is a matter of preference.
    // If you prefer hasAuthority('ADMIN'), you can keep the GrantedAuthorityDefaults bean.
}