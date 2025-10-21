package com.crio.rent_read.repository;

import java.util.Optional;
import com.crio.rent_read.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    
/**
     * Finds a user by their unique email address.
     * @param email The user's email.
     * @return An Optional containing the UserEntity if found.
     */
    Optional<UserEntity> findByEmail(String email);
}