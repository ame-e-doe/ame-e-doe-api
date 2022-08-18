package br.com.api.loveanddonateapi.exception.handler;

import br.com.api.loveanddonateapi.exception.EntityNotFoundException;
import br.com.api.loveanddonateapi.exception.InvalidJwtAuthenticationException;
import br.com.api.loveanddonateapi.exception.StanderError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
@RestController
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

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

    @ExceptionHandler( InvalidJwtAuthenticationException.class )
    public final ResponseEntity< StanderError > invalidJwtAuthenticationException( Exception ex, HttpServletRequest request ) {
        StanderError standerError =
                new StanderError(
                        Instant.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Expired or invalid token",
                        ex.getMessage(),
                        request.getRequestURI() );
        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body(standerError);
    }

}