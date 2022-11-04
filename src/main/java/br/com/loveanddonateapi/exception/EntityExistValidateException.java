package br.com.loveanddonateapi.exception;

public class EntityExistValidateException extends RuntimeException {

    public EntityExistValidateException( String msg ) {
        super( msg );
    }
}
