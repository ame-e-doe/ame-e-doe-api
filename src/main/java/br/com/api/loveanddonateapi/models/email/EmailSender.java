package br.com.api.loveanddonateapi.models.email;

public interface EmailSender {
    void send( String to, String email);
}
