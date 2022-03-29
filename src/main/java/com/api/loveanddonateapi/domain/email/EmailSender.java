package com.api.loveanddonateapi.domain.email;

public interface EmailSender {
    void send( String to, String email);
}
