package com.crio.rent_read.service;

import java.util.List;
import com.crio.rent_read.dto.RentalDto;

public interface RentalService {
    RentalDto rentBook(Long bookId, String userEmail);
    RentalDto returnBook(Long rentalId, String userEmail);
    List<RentalDto> getActiveRentalsForUser(String userEmail);
}
