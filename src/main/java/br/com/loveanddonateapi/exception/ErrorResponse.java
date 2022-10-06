package br.com.loveanddonateapi.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ErrorResponse {

    private List<ErrorObject> errors;
    public ErrorResponse( BindingResult bindingResult ) {

        this.errors = bindingResult.getFieldErrors().stream().map( error -> new ErrorObject( error.getDefaultMessage(), error.getField() ) ).collect( Collectors.toList() );

    }

    public ErrorResponse( String message, String error ) {
       errors = new ArrayList<>();
       ErrorObject errorObject = new ErrorObject( message, error );
       errors.add( errorObject );
    }

    public List< ErrorObject > getErrors() {
        return errors;
    }
}