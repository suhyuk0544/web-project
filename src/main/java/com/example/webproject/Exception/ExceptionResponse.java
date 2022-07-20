package com.example.webproject.Exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    private Date timestamp;

    private String message;

    private HttpStatus status;

    private String details;

}

