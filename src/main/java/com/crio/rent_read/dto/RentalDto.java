package com.crio.rent_read.dto;

import java.time.LocalDate;
import com.crio.rent_read.exchange.BookResponseDto;
import lombok.Data;

@Data
public class RentalDto {
    private Long id;
    private BookResponseDto book;// Nested DTO with book details
    private LocalDate rentalDate;
    private LocalDate returnDate;
}
