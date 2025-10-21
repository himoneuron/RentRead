package com.crio.rent_read.mapper;


import com.crio.rent_read.dto.BookCreateRequest;
import com.crio.rent_read.dto.RentalDto;
import com.crio.rent_read.dto.UserDto;
import com.crio.rent_read.exchange.BookResponseDto;
import com.crio.rent_read.model.BookEntity;
import com.crio.rent_read.model.RentalEntity;
import com.crio.rent_read.model.UserEntity;
import org.springframework.stereotype.Component;
@Component
public class EntityDtoMapper {

  
    public UserDto toUserDto(UserEntity user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        if (user.getRole() != null) {
            dto.setRole(user.getRole().getName());
        }
        return dto;
    }

 // Fixed: Added the 'BookEntity' return type
 public BookEntity toBookEntity(BookCreateRequest request) {
    BookEntity book = new BookEntity();
    book.setTitle(request.getTitle());
    book.setAuthor(request.getAuthor());
    book.setGenre(request.getGenre());
    // Added a null-safe check and conversion logic
    String status = request.getAvailabilityStatus();
    book.setAvailable(status != null && status.equalsIgnoreCase("AVAILABLE"));
    return book;
}

// Method to map from the entity to the response DTO
public BookResponseDto toBookResponseDto(BookEntity book) {
    BookResponseDto dto = new BookResponseDto();
    dto.setId(book.getId());
    dto.setTitle(book.getTitle());
    dto.setAuthor(book.getAuthor());
    dto.setGenre(book.getGenre());
   
    dto.setAvailabilityStatus(book.isAvailable() ? "AVAILABLE" : "UNAVAILABLE");
    return dto;
}

public RentalDto toRentalDto(RentalEntity rental) {
    RentalDto dto = new RentalDto();
    dto.setId(rental.getId());
    // Correct the method name here
    dto.setBook(toBookResponseDto(rental.getBook())); 
    dto.setRentalDate(rental.getRentalDate());
    dto.setReturnDate(rental.getReturnDate());
    // The rental DTO should also have the user's ID
    dto.setId(rental.getUser().getId());
    return dto;
}
}