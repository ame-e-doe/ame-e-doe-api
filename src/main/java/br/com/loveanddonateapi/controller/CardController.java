package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.dto.CardDTO;
import br.com.loveanddonateapi.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RestController
@AllArgsConstructor
@RequestMapping( path = "/api/card" )
@Api( tags = { "Cartões" } )
public class CardController {

    CardService cardService;

    @ApiOperation( value = "Cadastra um novo cartão" )
    @PostMapping( "/create" )
    @ResponseStatus( HttpStatus.CREATED )
    public ResponseEntity< CardDTO > create( @RequestHeader( "email" ) String email,
                                             @Valid @RequestBody CardDTO cardDTO ) {
        return new ResponseEntity<>( cardService.save(
                CardDTO.asEntity( cardDTO ),
                email ),
                HttpStatus.CREATED );
    }

    @ApiOperation( value = "Consulta cartão pelo identificador" )
    @GetMapping( "/{idCard}" )
    public ResponseEntity< CardDTO > getById( @PathVariable Long idCard ) {
        return ResponseEntity.ok( cardService.getById( idCard ) );
    }

    @ApiOperation( value = "Lista todos cartões de um usuario" )
    @GetMapping( "/list" )
    public ResponseEntity< List< CardDTO > > getAllCards( @RequestHeader( "email" ) String email ) {
        return ResponseEntity.ok( cardService.getAll( email ) );
    }

    @ApiOperation( value = "Deleta um cartão pelo identificador" )
    @DeleteMapping( "/{cardId}" )
    public ResponseEntity< Void > deleteCard( @PathVariable Long cardId ) {
        cardService.delete( cardId );
        return ResponseEntity.noContent().build();
    }

}