package com.api.loveanddonateapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler( EntityNotFoundException.class )
    public ResponseEntity< StanderError > entityNotFound( EntityNotFoundException e, HttpServletRequest request ) {
        StanderError error = new StanderError();
        error.setTimestamp( Instant.now() );
        error.setStatus( HttpStatus.NOT_FOUND.value() );
        error.setError( "Entidade não encontrada." );
        error.setMessage( e.getMessage() );
        error.setPath( request.getRequestURI() );
        return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( error );
    }
}
