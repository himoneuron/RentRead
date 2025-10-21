package com.crio.rent_read.repositoryService;

import com.crio.rent_read.model.UserEntity;

public interface UserRepositoryService {
    UserEntity registerUser(String firstName, String lastName, String email, String password, String role);
      
}

