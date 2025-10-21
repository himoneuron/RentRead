package com.crio.rent_read.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class BookCreateRequest {
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    private String genre;

    private String availabilityStatus;// Mapped from availabilityStatus
}