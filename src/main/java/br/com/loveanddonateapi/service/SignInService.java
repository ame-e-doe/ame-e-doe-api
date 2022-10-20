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

@Slf4j
@Service
@AllArgsConstructor
public class SignInService {

    AuthenticationManager authenticationManager;

    JwtUtils jwtUtils;

    private final UserRepository userRepository;

    public ResponseEntity< ? > auth( SignInDTO signInDTO ) {

        log.info( "Validate user is enabled and exists in database {}", signInDTO.getEmail() );

        if( !userRepository.existsByUsername( signInDTO.getEmail() ) ) {
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
                user.getUsername()
        ) );
    }

}