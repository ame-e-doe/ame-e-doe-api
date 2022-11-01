package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.configuration.jwt.JwtUtils;
import br.com.loveanddonateapi.configuration.response.JwtResponse;
import br.com.loveanddonateapi.dto.response.MessageResponse;
import br.com.loveanddonateapi.dto.user.PasswordDTO;
import br.com.loveanddonateapi.dto.user.SignInDTO;
import br.com.loveanddonateapi.exception.user.PasswordValidationException;
import br.com.loveanddonateapi.exception.user.UserExistsException;
import br.com.loveanddonateapi.mapper.UserMapper;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignInService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    private Authentication authentication;

    public ResponseEntity< ? > auth( SignInDTO signInDTO ) {

        log.info( "Validate user is enabled and exists in database {}", signInDTO.getEmail() );

        if( !userRepository.existsByUsername( signInDTO.getEmail() ) ) {
            log.error( "user not found by email {}", signInDTO.getEmail() );
            return ResponseEntity
                    .status( HttpStatus.UNAUTHORIZED )
                    .body( new MessageResponse( "O usuário não existe!" ) );
        }
        return authenticated( signInDTO );
    }

    private ResponseEntity< ? > authenticated( SignInDTO signInDTO ) {
        log.info( "Authenticate user {}", signInDTO.getEmail() );
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( signInDTO.getEmail(), signInDTO.getPassword() ) );
        SecurityContextHolder.getContext().setAuthentication( authentication );
        String jwt = jwtUtils.generateJwtToken( authentication );

        User user = ( User ) authentication.getPrincipal();
        return ResponseEntity.ok( new JwtResponse( jwt,
                user.getFirstName(),
                user.getUsername(),
                user.getId()
        ) );
    }

    public MessageResponse resetPassword( Long id, PasswordDTO passwordDTO ) throws PasswordValidationException {
        log.info( "get user by id {} in database", id );
        User user = userRepository.getById( id );

        if( !Objects.isNull( user ) || !ObjectUtils.isEmpty( user ) ) {
            user = updatePassword( passwordDTO, user );
        } else {
            throw new UserExistsException( "Usuário não encontrado" );
        }

        log.info( "update password user in database {}", user.getUsername() );
        userRepository.save( user );

        return MessageResponse.builder()
                .message( "Senha atualizada com sucesso" )
                .build();
    }

    private User updatePassword( PasswordDTO passwordDTO, User user ) throws PasswordValidationException {
        if ( isValidated( user.getUsername(), passwordDTO.getOldPassword() ) ) {
            user.setPassword( UserMapper
                    .encrypt()
                    .encode( passwordDTO.getNewPassword() ) );
        }
        return user;
    }

    public boolean isValidated( String username, String oldPassword ) throws PasswordValidationException {
        boolean isAuthenticated;
        try {
            log.info( "Validate user {}", username );
            isAuthenticated = authenticationManager
                    .authenticate( new UsernamePasswordAuthenticationToken( username, oldPassword ) )
                    .isAuthenticated();
        } catch( AuthenticationException e ) {
            throw new PasswordValidationException( "Senha antiga não confere" );
        }
        return isAuthenticated;
    }
}