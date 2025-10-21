package com.crio.rent_read.service;

import com.crio.rent_read.dto.UserDto;
import com.crio.rent_read.exchange.LoginRequest;
import com.crio.rent_read.exchange.RegisterRequest;

public interface AuthService {
    // This method comes from your UserRepositoryService
    UserDto registerUser(RegisterRequest request); 

    // This method handles login logic
    UserDto loginUser(LoginRequest request);

    UserDto findUserByEmail(String email);
}
