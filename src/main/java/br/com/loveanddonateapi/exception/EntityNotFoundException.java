package br.com.loveanddonateapi.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException( String msg ) {
        super( msg );
    }
}
