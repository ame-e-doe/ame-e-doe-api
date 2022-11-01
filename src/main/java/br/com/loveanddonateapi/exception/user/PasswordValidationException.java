package br.com.loveanddonateapi.exception.user;

import org.apache.http.auth.AuthenticationException;

public class PasswordValidationException extends AuthenticationException {

    public PasswordValidationException( String ex ) {
        super(ex);
    }
}
