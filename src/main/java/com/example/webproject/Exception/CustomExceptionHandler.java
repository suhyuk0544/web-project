package com.example.webproject.Exception;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Controller
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleServerExceptions(Exception ex, WebRequest request,Model model){

        ExceptionResponse exceptionResponse =

                new ExceptionResponse(new Date(), ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        model.addAttribute("Exception",exceptionResponse);

//        return "Exception.mustache";
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleUserNotfoundExceptions(Exception ex, WebRequest request,Model model){

        ExceptionResponse exceptionResponse =

                new ExceptionResponse(new Date(),ex.getMessage(),HttpStatus.NOT_FOUND, request.getDescription(false));

        model.addAttribute("Exception",exceptionResponse);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
