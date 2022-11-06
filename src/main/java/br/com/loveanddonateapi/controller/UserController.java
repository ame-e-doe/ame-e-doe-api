package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.dto.response.MessageResponse;
import br.com.loveanddonateapi.dto.user.PasswordDTO;
import br.com.loveanddonateapi.dto.user.UpdateUserDTO;
import br.com.loveanddonateapi.dto.user.UserDTO;
import br.com.loveanddonateapi.dto.user.UserDTOResponse;
import br.com.loveanddonateapi.exception.user.PasswordValidationException;
import br.com.loveanddonateapi.mapper.UserMapper;
import br.com.loveanddonateapi.service.SignInService;
import br.com.loveanddonateapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping( "/api/user" )
@Api( tags = "{Controle de usuário}" )
@Validated
@RequiredArgsConstructor
@CrossOrigin( origins = "*", maxAge = 3600 )
public class UserController {

    private final UserService userService;

    private final SignInService signInService;

    @PostMapping( "/register" )
    @ApiOperation( "Create user" )
    public ResponseEntity< UserDTOResponse > register( @Valid @RequestBody @ApiParam( required = true )
                                                       UserDTO userDTO ) {
        String email = userService.registerUser( UserMapper.userDtoToEntity( userDTO ) );
        return new ResponseEntity<>( UserDTOResponse.builder()
                .message( email )
                .build(), HttpStatus.CREATED );
    }

    @PutMapping( "/update" )
    @ApiOperation( "Update user" )
    public ResponseEntity< UpdateUserDTO > resetPassword( @RequestHeader( "idUser" ) Long idUser,
                                                          @Valid
                                                          @RequestBody
                                                          @ApiParam( required = true )
                                                          UpdateUserDTO updateUserDTO ) {

        UpdateUserDTO updatedUser = userService.updateUser( idUser, updateUserDTO );
        return new ResponseEntity<>( updatedUser, HttpStatus.OK );
    }

    @PutMapping( "/reset/password" )
    @ApiOperation( "Update password for user" )
    public ResponseEntity< MessageResponse > resetPassword( @RequestHeader( "idUser" ) Long idUser,
                                                            @Valid
                                                            @RequestBody
                                                            @ApiParam( required = true )
                                                            PasswordDTO passwordDTO ) throws PasswordValidationException {

        MessageResponse messageResponse = signInService.resetPassword( idUser, passwordDTO );
        return new ResponseEntity<>( messageResponse, HttpStatus.OK );
    }

    @DeleteMapping( "/delete" )
    @ApiOperation( "Delete user" )
    public ResponseEntity< ? > delete( @Valid @RequestHeader( "idUser" ) Long idUser ) {
        return new ResponseEntity<>( userService.deleteUser( idUser ),
                HttpStatus.NO_CONTENT );
    }

    @GetMapping( "/me" )
    @ApiOperation( "Get info user" )
    public ResponseEntity< ? > getById( @Valid @RequestHeader( "idUser" ) Long idUser ) {
        return ResponseEntity
                .ok( userService
                        .getById( idUser ) );
    }

}