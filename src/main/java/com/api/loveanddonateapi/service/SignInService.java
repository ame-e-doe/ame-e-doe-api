package com.api.loveanddonateapi.service;

import com.api.loveanddonateapi.security.jwt.JwtUtils;
import com.api.loveanddonateapi.dto.SignInDTO;
import com.api.loveanddonateapi.models.User;
import com.api.loveanddonateapi.security.response.JwtResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SignInService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity< ? > auth( SignInDTO signInDTO ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( signInDTO.getEmail(), signInDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = ( User ) authentication.getPrincipal();
        List<String> roles = user.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect( Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                user.getUsername(),
                roles));
    }

}
