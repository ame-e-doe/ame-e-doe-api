package com.api.loveanddonateapi.service;

import com.api.loveanddonateapi.domain.ConfirmationToken;
import com.api.loveanddonateapi.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken( ConfirmationToken confirmationToken ) {
        confirmationTokenRepository.save( confirmationToken );
    }

    public Optional<ConfirmationToken> getToken( String token ) {
        return confirmationTokenRepository.findByToken( token );
    }

    public int setConfirmedAt( String token ) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now()
        );
    }
    
}