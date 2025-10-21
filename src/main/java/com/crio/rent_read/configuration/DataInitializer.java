package com.crio.rent_read.configuration;

import com.crio.rent_read.model.RoleEntity;
import com.crio.rent_read.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if roles already exist to avoid creating duplicates
        if (roleRepository.findByName("USER").isEmpty()) {
            roleRepository.save(new RoleEntity(null, "USER"));
            System.out.println("Created USER role.");
        }
        
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            roleRepository.save(new RoleEntity(null, "ADMIN"));
            System.out.println("Created ADMIN role.");
        }
    }
    
}
