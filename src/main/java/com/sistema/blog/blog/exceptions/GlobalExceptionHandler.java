package com.sistema.blog.blog.exceptions;

import com.sistema.blog.blog.dto.ErrorDetalles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    //
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetalles> manejarResourceNotFoundException(
            ResourceNotFoundException exception, WebRequest webRequest){

        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(),exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles, HttpStatus.NOT_FOUND);
    }
    //
    @ExceptionHandler(blogAppExeption.class)
    public ResponseEntity<ErrorDetalles> manejarblogAppExeption(
            blogAppExeption exception, WebRequest webRequest){

        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(),exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles, HttpStatus.BAD_REQUEST);
    }

    //
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarGlobalException(
            Exception exception, WebRequest webRequest){

        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(),exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
