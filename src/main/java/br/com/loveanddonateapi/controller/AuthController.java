package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.dto.signin.SignInDTO;
import br.com.loveanddonateapi.dto.signin.signup.SignUpDTO;
import br.com.loveanddonateapi.repository.UserRepository;
import br.com.loveanddonateapi.service.SignInService;
import br.com.loveanddonateapi.service.SignUpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RestController
@RequestMapping( "/api/auth" )
@Api(tags = {"Autenticação"})
public class AuthController {

    @Autowired
    SignUpService signUpService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SignInService signInService;

    @ApiOperation( value = "Realiza registro de novos usuários" )
    @PostMapping( "/signup" )
    @ResponseStatus( HttpStatus.CREATED )
    public ResponseEntity< ? > registerUser( @Valid @RequestBody SignUpDTO signupDTO ) {
        return signUpService.signUp( signupDTO );
    }

    @ApiOperation( value = "Valida autenticidade do usuário e retorna token de acesso")
    @PostMapping( "/signin" )
    public ResponseEntity< ? > authenticateUser( @Valid @RequestBody SignInDTO signInDTO ) {
        return signInService.auth( signInDTO );
    }

    @ApiOperation( "Confirma registro" )
    @GetMapping( "/confirm" )
    public ResponseEntity< ? > confirm( @RequestParam( "token" ) String token ) {
        return signUpService.confirmToken( token );
    }

}