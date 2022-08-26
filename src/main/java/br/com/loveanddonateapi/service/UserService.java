package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.exception.RoleNotFoundException;
import br.com.loveanddonateapi.exception.user.UserExistsException;
import br.com.loveanddonateapi.mapper.UserMapper;
import br.com.loveanddonateapi.models.Role;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.models.email.Email;
import br.com.loveanddonateapi.models.enums.ERole;
import br.com.loveanddonateapi.repository.RoleRepository;
import br.com.loveanddonateapi.repository.UserRepository;
import br.com.loveanddonateapi.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    public Email registerUser( User user ) {

        if( userRepository.existsByEmail( user.getEmail() ) ) {
            throw new UserExistsException( "Usuário já registrado" );
        }
        createUser( user );
        confirmationTokenService
                .saveConfirmationToken( user );

        return Email.builder()
                .email( EmailUtils.formatterEmail( user.getEmail() ) )
                .build();
    }

    private void createUser( User user ) {
        user.setPassword( UserMapper.encrypt().encode( user.getPassword() ) );
        user.setRoles( generateRole( null ) );
        userRepository.save( user );
    }

    private List<Role> generateRole( String role ) {
        List<Role> roles = new ArrayList<>();

        if( Objects.isNull( role ) ) {
            Role userRole = roleRepository.findByName( ERole.ROLE_USER.name() )
                    .orElseThrow( () -> new RoleNotFoundException( "Role não encontrada" ) );
            roles.add( userRole );
        }
        return roles;
    }

    @Transactional
    public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException {
        var user = userRepository.findByEmail( email )
                .orElseThrow( () -> new UsernameNotFoundException( "User not found with email: " + email ) );

        return user;
    }

    public int enableUser( String email ) {
        return userRepository.enableUser( email );
    }

    public User getById(Long id) {
        return userRepository.getById(id);
    }
}