package com.api.loveanddonateapi.controller;

import com.api.loveanddonateapi.domain.SignInRequest;
import com.api.loveanddonateapi.domain.SignUpRequest;
import com.api.loveanddonateapi.service.SignInService;
import com.api.loveanddonateapi.service.SignUpService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RestController
@AllArgsConstructor
@RequestMapping( path = "api/auth" )
public class AuthController {

    @Autowired
    SignUpService signUpService;

    @Autowired
    SignInService signInService;

    @PostMapping( "/signup" )
    public ResponseEntity< ? > registerUser( @Valid @RequestBody SignUpRequest signupRequest ) {
        return signUpService.signUp( signupRequest );
    }

    @PostMapping( "/signin")
    public ResponseEntity< ? > authorize( @Valid @RequestBody SignInRequest signInRequest ) {
        return signInService.auth( signInRequest );
    }

    @GetMapping(path = "confirm")
    public String confirm( @RequestParam( "token" ) String token ) {
        return signUpService.confirmToken( token );
    }

}