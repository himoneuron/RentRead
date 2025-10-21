package com.crio.rent_read.exchange;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class BookRequest {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Author cannot be blank")
    private String author;

    private String genre;
}