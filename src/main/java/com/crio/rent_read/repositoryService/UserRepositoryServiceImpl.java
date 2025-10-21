package com.crio.rent_read.repositoryService;

import com.crio.rent_read.exception.EmailExistException;
import com.crio.rent_read.exception.resourceNotFoundException;
import com.crio.rent_read.model.RoleEntity;
import com.crio.rent_read.model.UserEntity;
import com.crio.rent_read.repository.RoleRepository;
import com.crio.rent_read.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryServiceImpl implements UserRepositoryService{


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserEntity registerUser(String firstName, String lastName, String email, String password,
            String role) {
                if (userRepository.findByEmail(email).isPresent()) {
                    throw new EmailExistException("An account with this email already exists.");
                }
                // 2. Create the UserEntity
        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Hash the password

        // 3. Find and set the role, defaulting to "USER"
        String roleName = (role != null && !role.isEmpty()) ? role.toUpperCase() : "USER";
        RoleEntity userRole = roleRepository.findByName(roleName)
                .orElseThrow(() -> new resourceNotFoundException("Role not found: " + roleName));
        user.setRole(userRole);

        // 4. Save the user to the database and return the persisted entity
        return userRepository.save(user);
    }
    
}
