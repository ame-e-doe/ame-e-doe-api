package com.api.loveanddonateapi.controller;

import com.api.loveanddonateapi.dto.SignInRequest;
import com.api.loveanddonateapi.dto.signup.SignUpDTO;
import com.api.loveanddonateapi.service.SignInService;
import com.api.loveanddonateapi.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RestController
@RequestMapping( path = "api/auth" )
public class AuthController {

    @Autowired
    SignUpService signUpService;

    @Autowired
    SignInService signInService;

    @PostMapping( "/signup" )
    public ResponseEntity< ? > registerUser( @Valid @RequestBody SignUpDTO signupDTO ) {
        return signUpService.signUp( signupDTO );
    }

    @PostMapping( "/signin")
    public ResponseEntity< ? > authorize( @Valid @RequestBody SignInRequest signInRequest ) {
        return signInService.auth( signInRequest );
    }

    @GetMapping( "/confirm" )
    public String confirm( @RequestParam( "token" ) String token ) {
        return signUpService.confirmToken( token );
    }

}