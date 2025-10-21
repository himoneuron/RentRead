* DataInitializer (Data Seeding)
  This class uses the CommandLineRunner interface.

**Its purpose here:** It's used for "seeding" the database with essential starting data. In your code, it checks if the "USER" and "ADMIN" roles exist in your `roles` table. If they don't, it creates them. This prevents errors later when you try to assign these roles to new users.

* The `UserDetailsServiceImpl` (The User Lookup Service)


* **What it does:** This class acts as the bridge between your custom `UserEntity` and Spring Security's internal user system. Its only job is to provide a method, `loadUserByUsername`, that tells Spring Security how to find a user in your database.
* **When it runs:** This code is executed  **during every login attempt** . When a user tries to log in with their email and password, Spring Security calls your `loadUserByUsername` method with the provided email. It then uses the returned `UserDetails` object (which contains the correct hashed password from your database) to verify if the submitted password is correct.

**Analogy:** Think of it as the personnel office for a secure facility. When someone tries to enter (log in), the security guard (Spring Security) calls your `UserDetailsServiceImpl` to pull that person's file. The guard then checks their ID against the file.




package com.crio.rent_read.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Bean to encode passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean that defines all security rules
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless REST APIs
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll() // Allow all requests to /auth/... (e.g., signup, login)
                .anyRequest().authenticated() // All other requests must be authenticated
            );

    // Spring will automatically use your UserDetailsServiceImpl because it's a bean

    return http.build();
    }
}
