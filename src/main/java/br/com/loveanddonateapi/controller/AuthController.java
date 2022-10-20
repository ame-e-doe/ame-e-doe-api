package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.dto.user.SignInDTO;
import br.com.loveanddonateapi.service.SignInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RestController
@RequestMapping( "/api/auth" )
@Api(tags = {"Autenticação"})
public class AuthController {

    @Autowired
    SignInService signInService;

    @PostMapping( "/login" )
    @ApiOperation( value = "Realiza login")
    public ResponseEntity< ? > authenticateUser( @Valid @RequestBody SignInDTO signInDTO ) {
        return signInService.auth( signInDTO );
    }

}