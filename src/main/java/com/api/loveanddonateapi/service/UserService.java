package com.api.loveanddonateapi.service;

import com.api.loveanddonateapi.domain.ConfirmationToken;
import com.api.loveanddonateapi.repository.UserRepository;
import com.api.loveanddonateapi.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "user with email %s not found";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername( String email )
            throws UsernameNotFoundException {
        return userRepository.findByEmail( email )
                .orElseThrow( () ->
                        new UsernameNotFoundException( String.format( USER_NOT_FOUND, email ) ) );
    }

    public String signUpUser( User user ) {
        boolean userExists =  userRepository
                .findByEmail( user.getEmail() )
                .isPresent();

        if( userExists ) {
//            TODO: Implementar lógica, se o e-mail já existe na base porém o usuário ainda não confirmou o token
//            TODO: Devemos reenviar o e-mail com um token novo
            throw new IllegalStateException( "email already taken" );
        }

        String encodePassword = bCryptPasswordEncoder.encode( user.getPassword() );

        user.setPassword( encodePassword );

        userRepository.save( user );

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes( 15 ),
                user
        );

        confirmationTokenService.saveConfirmationToken( confirmationToken );

//        TODO: SEND E-MAIL

        return token;
    }

    public int enableAppUser( String email ) {
        return userRepository.enableUser( email );
    }

    public Optional< User > getUserById( Long userId) {
        return this.userRepository.findById( userId.longValue() );
    }
}
