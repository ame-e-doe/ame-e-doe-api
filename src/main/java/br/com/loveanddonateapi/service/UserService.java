package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException {
        log.debug( "Find user by email {}", email );
        var user = userRepository.findByEmail( email )
                .orElseThrow( () -> new UsernameNotFoundException( "User not found with email: " + email ) );

        return user;
    }

    public int enableUser( String email ) {
        log.debug( "Enable user with email in databse {}", email );
        return userRepository.enableUser( email );
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado."));
    }

}