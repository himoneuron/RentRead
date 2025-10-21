package com.crio.rent_read.controller;

import java.security.Principal;
import java.util.List;
import com.crio.rent_read.dto.RentalDto;
import com.crio.rent_read.exception.UnauthorizedException;
import com.crio.rent_read.exception.resourceNotFoundException;

import com.crio.rent_read.model.UserEntity;
import com.crio.rent_read.repository.UserRepository;
import com.crio.rent_read.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    @Autowired 
    private RentalService rentalService;
    @Autowired 
    private UserRepository userRepository;
  

    @PostMapping("/users/{userId}/books/{bookId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<RentalDto> rentBook(@PathVariable Long userId, @PathVariable Long bookId, Principal principal) {
        // Security Check: Ensure the logged-in user matches the user in the path
        UserEntity user = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new resourceNotFoundException("Authenticated user not found."));
        if (!user.getId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to rent a book for this user ID.");
        }
        
        RentalDto rental = rentalService.rentBook(bookId, principal.getName());
        return new ResponseEntity<>(rental, HttpStatus.CREATED);
    }

    @GetMapping("/active-rentals/users/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<RentalDto>> getActiveRentals(@PathVariable Long userId, Principal principal) {
        // Security Check: Ensure the logged-in user matches the user in the path
        UserEntity user = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new resourceNotFoundException("Authenticated user not found."));
        if (!user.getId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to view rentals for this user ID.");
        }

        List<RentalDto> activeRentals = rentalService.getActiveRentalsForUser(principal.getName());
        return new ResponseEntity<>(activeRentals, HttpStatus.OK);
    }

    @PutMapping("/{rentalId}") 
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> returnBook(@PathVariable Long rentalId, Principal principal) {
    // The service call remains the same
    rentalService.returnBook(rentalId, principal.getName());
    
    // Return an empty response with a 204 No Content status
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

}
