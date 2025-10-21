package com.crio.rent_read.repository;

import java.util.List;
import com.crio.rent_read.model.RentalEntity;
import com.crio.rent_read.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<RentalEntity, Long>{
     /**
     * Counts the number of active rentals for a specific user.
     * An active rental is one where the return date is NULL.
     * This method directly supports the business rule: "A user cannot have more than two active rentals".
     * @param user The user whose active rentals are to be counted.
     * @return The number of active rentals.
     */
    long countByUserAndReturnDateIsNull(UserEntity user);
    
    /**
     * Finds an active rental for a specific user and book.
     * This is used when a user tries to return a book.
     * @param user The user who rented the book.
     * @param book The book that was rented.
     * @return An Optional containing the active RentalEntity if found.
     */
    List<RentalEntity> findByUserAndReturnDateIsNull(UserEntity user);
}
