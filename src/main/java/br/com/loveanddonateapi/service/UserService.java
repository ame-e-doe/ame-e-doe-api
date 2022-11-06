package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.dto.user.UpdateUserDTO;
import br.com.loveanddonateapi.exception.user.RoleNotFoundException;
import br.com.loveanddonateapi.exception.user.UserExistsException;
import br.com.loveanddonateapi.mapper.UserMapper;
import br.com.loveanddonateapi.models.Role;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.models.enums.ERole;
import br.com.loveanddonateapi.repository.RoleRepository;
import br.com.loveanddonateapi.repository.UserRepository;
import br.com.loveanddonateapi.utils.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Transactional
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CartService cartService;

    public String registerUser( User user ) {

        log.info( "saving new user {} ", user.getUsername() );
        if( userRepository.existsByUsername( user.getUsername() ) ) {
            throw new UserExistsException( "Usuário já registrado" );
        }
        createUser( user );

        return EmailUtils.formatterEmail( user.getUsername() );
    }

    protected void createUser( User user ) {

        log.info( "generate info for user {} and props", user.getUsername() );
        user.setPassword( UserMapper
                .encrypt()
                .encode(
                        user.getPassword() ) );

        user.setRoles( generateRole( null ) );

        User userSaved = userRepository.save( user );

        cartService.createCart( userSaved );

    }

    public UpdateUserDTO updateUser( Long id, UpdateUserDTO updateUserDTO ) {
        log.info( "get user by id {} in database", id );
        User user = userRepository.getById( id );

        if( !Objects.isNull( user ) || !ObjectUtils.isEmpty( user ) ) {
            user.setFirstName( updateUserDTO.getFirstName() );
            user.setLastName( updateUserDTO.getLastName() );
        } else {
            throw new UserExistsException( "Usuário não encontrado" );
        }

        log.info( "update user in database {}", user.getUsername() );
        User userUpdated = userRepository.save( user );

        return UpdateUserDTO.builder()
                .firstName( userUpdated.getFirstName() )
                .lastName( userUpdated.getLastName() )
                .build();
    }

    public ResponseEntity< ? > deleteUser( Long id ) {
        User user = userRepository.getById( id );

        if( !Objects.isNull( user ) || !ObjectUtils.isEmpty( user ) ) {
            userRepository.delete( user );
        } else {
            throw new UserExistsException( "Usuário não encontrado" );
        }

        return new ResponseEntity<>( HttpStatus.NO_CONTENT );

    }

    private List< Role > generateRole( String role ) {
        List< Role > roles = new ArrayList<>();

        if( Objects.isNull( role ) ) {
            log.info( "add role a new user" );
            Role userRole = roleRepository.findByName( ERole.ROLE_USER.name() )
                    .orElseThrow( () -> new RoleNotFoundException( "Role não encontrada" ) );
            roles.add( userRole );
        }
        return roles;
    }

    public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException {
        var user = userRepository.findByUsername( email );

        if( Objects.isNull( user ) ) {
            log.error( "User not found: " );
            throw new UsernameNotFoundException( "Usuário não encontrado para o email: " + email );
        }
        log.info( "User found by email: {}", email );
        return user;
    }

    public User getById( Long id ) {
        log.info( "localize user {} by id", id );
        return userRepository.findById( id ).orElseThrow(() -> new UserExistsException("Usuario não encontrado!"));
    }

    public void saveRole( Role role ) {
        roleRepository.save( role );
    }

}