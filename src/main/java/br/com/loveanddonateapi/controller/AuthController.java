package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.dto.user.SignInDTO;
import br.com.loveanddonateapi.service.SignInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/auth" )
@Api(tags = {"Autenticação"})
public class AuthController {

    private final SignInService signInService;

    @PostMapping( "/login" )
    @ApiOperation( value = "Realiza login")
    public ResponseEntity< ? > authenticateUser( @Valid @RequestBody SignInDTO signInDTO ) {
        return signInService.auth( signInDTO );
    }

}