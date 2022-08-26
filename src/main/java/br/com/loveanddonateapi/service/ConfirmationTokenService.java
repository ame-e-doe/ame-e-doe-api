package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.models.ConfirmationToken;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(  User user ) {
        confirmationTokenRepository.save( generateConfirmationToken( user ) );
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

    private ConfirmationToken generateConfirmationToken( User user ) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken( token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes( 15 ),
                user );
        log.debug( "Send email for this user {}", user.getEmail() );
//        TODO: Ajustar lógica para envio de email
//        sendEmail( user.getEmail(), token );

        return confirmationToken;
    }
    
}