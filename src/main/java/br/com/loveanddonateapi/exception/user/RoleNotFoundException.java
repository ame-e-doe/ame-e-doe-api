package br.com.loveanddonateapi.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.BAD_REQUEST )
public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException( String ex ) {
        super( ex );
    }

}
