package br.com.loveanddonateapi.exception.handler;

import br.com.loveanddonateapi.exception.*;
import br.com.loveanddonateapi.exception.user.UserExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler( EntityNotFoundException.class )
    public ResponseEntity< ErrorResponse > entityNotFound( EntityNotFoundException e, HttpServletRequest request ) {
        return ResponseEntity.status( HttpStatus.NOT_FOUND.value() )
                .body( new ErrorResponse( e.getMessage(), request.getRequestURI() ) );
    }

    @ExceptionHandler( UserExistsException.class )
    public ResponseEntity< ErrorResponse > userExistsException( UserExistsException e, HttpServletRequest request) {
        return ResponseEntity.status( HttpStatus.BAD_REQUEST.value() )
                .body( new ErrorResponse( e.getMessage(), request.getRequestURI() ) );
    }

    @ExceptionHandler( InvalidJwtAuthenticationException.class )
    public final ResponseEntity< ErrorResponse > invalidJwtAuthenticationException( Exception ex, HttpServletRequest request ) {
        return ResponseEntity.status( HttpStatus.BAD_REQUEST.value() )
                .body( new ErrorResponse( ex.getMessage(), request.getRequestURI() ) );
    }

    @ExceptionHandler( EntityExistValidateException.class )
    public ResponseEntity< ErrorResponse > entityExist( EntityExistValidateException e, HttpServletRequest request ) {
        return ResponseEntity.status( HttpStatus.BAD_REQUEST.value() )
                .body( new ErrorResponse( e.getMessage(), request.getRequestURI() ) );
    }

    @ExceptionHandler( MaxUploadSizeExceededException.class )
    public ResponseEntity< ErrorResponse > uploadSizeExceededException( MaxUploadSizeExceededException e, HttpServletRequest request ) {
        return ResponseEntity.status( HttpStatus.BAD_REQUEST.value() )
                .body( new ErrorResponse( e.getMessage(), request.getRequestURI() ) );
    }

    @ExceptionHandler( InvalidFileException.class )
    public ResponseEntity< ErrorResponse > uploadSizeExceededException( InvalidFileException e, HttpServletRequest request ) {
        return ResponseEntity.status( HttpStatus.BAD_REQUEST.value() )
                .body( new ErrorResponse( e.getMessage(), request.getRequestURI() ) );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request ) {

        return ResponseEntity.status( HttpStatus.BAD_REQUEST )
                .body( new ErrorResponse( ex.getBindingResult() ) );
    }

}