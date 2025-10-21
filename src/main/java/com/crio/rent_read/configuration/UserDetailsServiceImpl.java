package com.crio.rent_read.configuration;


import com.crio.rent_read.model.UserEntity;
import com.crio.rent_read.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // This is the only method we need to implement for Spring Security
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // ... find user by email ...
        UserEntity user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Create a list of authorities (roles) for the user
        List<GrantedAuthority> authorities = Collections.singletonList(
            // ADD THE "ROLE_" PREFIX HERE
            new SimpleGrantedAuthority("ROLE_" + user.getRole().getName())
        );

        // ... return UserDetails object ...
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
