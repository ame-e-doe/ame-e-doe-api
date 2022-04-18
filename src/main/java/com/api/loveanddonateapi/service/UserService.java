package com.api.loveanddonateapi.service;

import com.api.loveanddonateapi.domain.User;
import com.api.loveanddonateapi.dto.UserDTO;
import com.api.loveanddonateapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "user with email %s not found";

    @Autowired
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException {

        User user = userRepository.findByEmail( email )
                .orElseThrow( () -> new UsernameNotFoundException( String.format( USER_NOT_FOUND, email ) ) );

        return UserDTO.build(user);
    }

    public int enableAppUser( String email ) {
        return userRepository.enableUser( email );
    }

    public Optional< User > getUserById( Long userId) {
        return this.userRepository.findById( userId.longValue() );
    }
}
