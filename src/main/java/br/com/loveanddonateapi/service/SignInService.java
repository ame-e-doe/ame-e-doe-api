package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.configuration.jwt.JwtUtils;
import br.com.loveanddonateapi.configuration.response.JwtResponse;
import br.com.loveanddonateapi.dto.response.MessageResponse;
import br.com.loveanddonateapi.dto.user.SignInDTO;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class SignInService {

    AuthenticationManager authenticationManager;

    JwtUtils jwtUtils;

    UserRepository userRepository;

    public ResponseEntity< ? > auth( SignInDTO signInDTO ) {

        log.debug( "Validate user is enabled and exists in database {}", signInDTO.getEmail() );
        Boolean isEnabled = userRepository.isEnabled( signInDTO.getEmail() );

        if( Objects.equals( isEnabled, false ) ) {
            return ResponseEntity
                    .status( HttpStatus.UNAUTHORIZED )
                    .body( new MessageResponse( "O usuário não está ativo! Por favor confirme seu e-mail e tente novamente." ) );
        } else if( !userRepository.existsByEmail( signInDTO.getEmail() ) ) {
            return ResponseEntity
                    .status( HttpStatus.UNAUTHORIZED )
                    .body( new MessageResponse( "O usuário não existe!" ) );
        }
        return authenticated( signInDTO );
    }

    private ResponseEntity< ? > authenticated( SignInDTO signInDTO ) {
        log.debug( "Authenticate user {}", signInDTO.getEmail() );
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( signInDTO.getEmail(), signInDTO.getPassword() ) );
        SecurityContextHolder.getContext().setAuthentication( authentication );
        String jwt = jwtUtils.generateJwtToken( authentication );

        User user = ( User ) authentication.getPrincipal();
        return ResponseEntity.ok( new JwtResponse( jwt,
                user.getEmail()
        ) );
    }

}