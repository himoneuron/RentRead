package com.crio.rent_read.exchange;

import lombok.Data;

@Data
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private String availabilityStatus;
}