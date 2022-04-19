package com.api.loveanddonateapi.controller;

import com.api.loveanddonateapi.dto.SignInDTO;
import com.api.loveanddonateapi.dto.signup.SignUpDTO;
import com.api.loveanddonateapi.dto.signup.SignUpDTOResponse;
import com.api.loveanddonateapi.exception.SignUpException;
import com.api.loveanddonateapi.models.email.Email;
import com.api.loveanddonateapi.repository.UserRepository;
import com.api.loveanddonateapi.service.SignInService;
import com.api.loveanddonateapi.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RestController
@RequestMapping( path = "/auth" )
public class AuthController {

    @Autowired
    SignUpService signUpService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SignInService signInService;

    @PostMapping( "/signup" )
    public ResponseEntity< SignUpDTOResponse > registerUser( @Valid @RequestBody SignUpDTO signupDTO ) {
        if( userRepository.existsByEmail( signupDTO.getEmail() ) ) {
            throw new SignUpException( "Error: Email is already in use!" );
        }

        Email email = signUpService.signUp( signupDTO );

        return ResponseEntity.ok( new SignUpDTOResponse( email ) );

    }

    @PostMapping( "/signin" )
    public ResponseEntity authorize( @Valid @RequestBody SignInDTO signInDTO ) {

        return signInService.auth( signInDTO );

    }

    @GetMapping( "/confirm" )
    public ResponseEntity< ? > confirm( @RequestParam( "token" ) String token ) {
        return signUpService.confirmToken( token );
    }

}