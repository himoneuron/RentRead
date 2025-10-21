package com.crio.rent_read.service.serviceImpl;

import com.crio.rent_read.dto.RentalDto;
import com.crio.rent_read.exception.BookUnavailableException;
import com.crio.rent_read.exception.RentalLimitExceededException;
import com.crio.rent_read.exception.UnauthorizedException;
import com.crio.rent_read.exception.resourceNotFoundException;
import com.crio.rent_read.mapper.EntityDtoMapper;
import com.crio.rent_read.model.BookEntity;
import com.crio.rent_read.model.RentalEntity;
import com.crio.rent_read.model.UserEntity;
import com.crio.rent_read.repository.BookRepository;
import com.crio.rent_read.repository.RentalRepository;
import com.crio.rent_read.repository.UserRepository;
import com.crio.rent_read.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {
    @Autowired 
    private RentalRepository rentalRepository;
    @Autowired 
    private BookRepository bookRepository;
    @Autowired 
    private UserRepository userRepository;
    @Autowired 
    private EntityDtoMapper mapper;

    // A user can't rent more than two books at a time
    private static final int RENTAL_LIMIT = 2;

    @Override
    public RentalDto rentBook(Long bookId, String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new resourceNotFoundException("User not found: " + userEmail));

        // Check the rental limit
        if (rentalRepository.countByUserAndReturnDateIsNull(user) >= RENTAL_LIMIT) {
            throw new RentalLimitExceededException("You have reached the maximum rental limit of two books.");
        }

        BookEntity book = bookRepository.findById(bookId)
            .orElseThrow(() -> new resourceNotFoundException("Book not found with ID: " + bookId));
        
        if (!book.isAvailable()) {
            throw new BookUnavailableException("This book is currently unavailable.");
        }

        // Update book availability
        book.setAvailable(false);
        bookRepository.save(book);

        // Create the rental record
        RentalEntity newRental = new RentalEntity();
        newRental.setUser(user);
        newRental.setBook(book);
        newRental.setRentalDate(LocalDate.now());

        RentalEntity savedRental = rentalRepository.save(newRental);
        System.out.println("New rental created with ID: " + savedRental.getId()); 
        return mapper.toRentalDto(savedRental);
    }
    
    @Override
    public RentalDto returnBook(Long rentalId, String userEmail) {
        // Find the rental record
        RentalEntity rental = rentalRepository.findById(rentalId)
            .orElseThrow(() -> new resourceNotFoundException("Rental record not found with ID: " + rentalId));
        
        // Check if the user is the one who rented the book
        if (!rental.getUser().getEmail().equals(userEmail)) {
            throw new UnauthorizedException("You are not authorized to return this book.");
        }

        // Check if the book has already been returned
        if (rental.getReturnDate() != null) {
            throw new IllegalStateException("This book has already been returned.");
        }

        // Update the book's availability status
        BookEntity book = rental.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        // Update the rental's return date
        rental.setReturnDate(LocalDate.now());
        rentalRepository.save(rental);

        return mapper.toRentalDto(rental);
    }

    @Override
    public List<RentalDto> getActiveRentalsForUser(String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new resourceNotFoundException("User not found: " + userEmail));
        
        List<RentalEntity> activeRentals = rentalRepository.findByUserAndReturnDateIsNull(user);
        
        return activeRentals.stream()
            .map(mapper::toRentalDto)
            .collect(Collectors.toList());
    }
}