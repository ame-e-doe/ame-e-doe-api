package com.api.loveanddonateapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.BAD_REQUEST )
public class SignUpException extends AuthenticationException {
    public SignUpException( String exception ) {
        super( exception );
    }
}
