package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.dto.user.UserDTO;
import br.com.loveanddonateapi.dto.user.UserDTOResponse;
import br.com.loveanddonateapi.mapper.UserMapper;
import br.com.loveanddonateapi.models.email.Email;
import br.com.loveanddonateapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping( "/api/user" )
@Api( tags = "{Controle de usuário}")
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    @ApiOperation( "Create user" )
    public ResponseEntity< UserDTOResponse > register( @Valid @RequestBody @ApiParam( required = true )
                                                       UserDTO userDTO ) {
        Email mail = userService.registerUser( UserMapper.userDtoToEntity( userDTO ) );
        return new ResponseEntity<>( UserDTOResponse.builder()
                .message( mail )
                .build(), HttpStatus.CREATED );
    }

}