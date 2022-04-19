package com.api.loveanddonateapi.service;

import com.api.loveanddonateapi.models.User;
import com.api.loveanddonateapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException {

        User user = userRepository.findByEmail( email )
                .orElseThrow( () -> new UsernameNotFoundException( "User not found with email: " + email ) );

        return user;
    }

    public int enableUser( String email ) {
        return userRepository.enableUser( email );
    }

}