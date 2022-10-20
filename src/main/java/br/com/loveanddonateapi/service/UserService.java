package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.exception.user.RoleNotFoundException;
import br.com.loveanddonateapi.exception.user.UserExistsException;
import br.com.loveanddonateapi.mapper.UserMapper;
import br.com.loveanddonateapi.models.Role;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.models.enums.ERole;
import br.com.loveanddonateapi.repository.RoleRepository;
import br.com.loveanddonateapi.repository.UserRepository;
import br.com.loveanddonateapi.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final CartService cartService;

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

    private List<Role> generateRole( String role ) {
        List<Role> roles = new ArrayList<>();

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
            throw new UsernameNotFoundException( "Usuário não encontrado para o email: " + email );
        }
        return user;
    }

    public User getUser( String username ) {
        log.info( "localize user {} by username", username );
       return userRepository.findByUsername( username );
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado."));
    }

    public void saveRole( Role role ) {
        roleRepository.save( role );
    }

}