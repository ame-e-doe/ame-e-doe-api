package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.models.ConfirmationToken;
import br.com.loveanddonateapi.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken( ConfirmationToken confirmationToken ) {
        log.debug( "Save confirmation token in database {}", confirmationToken );
        confirmationTokenRepository.save( confirmationToken );
    }

    public Optional<ConfirmationToken> getToken( String token ) {
        log.debug( "Get Optional token in database by token {}", token );
        return confirmationTokenRepository.findByToken( token );
    }

    public int setConfirmedAt( String token ) {
        log.debug( "update field confirmedAt for confirmation token in database by token {}", token );
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now()
        );
    }
    
}