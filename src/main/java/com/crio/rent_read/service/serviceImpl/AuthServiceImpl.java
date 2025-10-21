package com.crio.rent_read.service.serviceImpl;

import com.crio.rent_read.dto.UserDto;
import com.crio.rent_read.exchange.LoginRequest;
import com.crio.rent_read.exchange.RegisterRequest;
import com.crio.rent_read.mapper.EntityDtoMapper;
import com.crio.rent_read.model.UserEntity;
import com.crio.rent_read.repository.UserRepository;
import com.crio.rent_read.repositoryService.UserRepositoryService;
import com.crio.rent_read.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired private 
    EntityDtoMapper mapper; 
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;


    
    @Override
    //unpacking our dto 
    public UserDto registerUser(RegisterRequest request) {
        UserEntity registeredUser = userRepositoryService.registerUser(
            request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), request.getRole());

        // Use the injected mapper instance
        return mapper.toUserDto(registeredUser);
    }

    @Override
    public UserDto loginUser(LoginRequest request) {
        // 1. Create an authentication token with the user's credentials
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
            request.getEmail(), 
            request.getPassword()
        );

        // 2. Authenticate the user. Spring Security will use your UserDetailsServiceImpl
        // and PasswordEncoder to validate the credentials.
        // If credentials are bad, it will throw an exception.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        
        // 3. (Optional) Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. If authentication is successful, fetch the user details to return
        UserEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found after successful login"));

        // 5. Map the entity to a DTO for the response
        return mapper.toUserDto(userEntity);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
