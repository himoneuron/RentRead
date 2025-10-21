package com.crio.rent_read.controller;

import jakarta.validation.Valid;
//import com.crio.rent_read.dto.LoginRequest;
import com.crio.rent_read.dto.UserDto;
import com.crio.rent_read.exchange.LoginRequest;
import com.crio.rent_read.exchange.RegisterRequest;
import com.crio.rent_read.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")


public class AuthController 
{
    @Autowired
    private AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody RegisterRequest request){
        UserDto registerUser = authService.registerUser(request);

        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
        
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@Valid @RequestBody LoginRequest request) {
        UserDto userDto = authService.loginUser(request);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }



    
}
