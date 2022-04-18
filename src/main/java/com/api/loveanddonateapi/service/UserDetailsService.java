package com.api.loveanddonateapi.service;

import com.api.loveanddonateapi.domain.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    User loadUserByUsername( String email ) throws UsernameNotFoundException;
}
