package com.crio.rent_read.repository;

import java.util.Optional;
import com.crio.rent_read.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    /**
     * Finds a role by its name (e.g., "USER" or "ADMIN").
     * @param name The name of the role.
     * @return An Optional containing the RoleEntity if found.
     */
    Optional<RoleEntity> findByName(String name);
}
