package com.crio.rent_read.exception;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString(), etc.
@NoArgsConstructor // Generates a no-argument constructor
public class ErrorResponse {

    private String message;
    private String httpStatus;
    private LocalDateTime localDateTime;

}