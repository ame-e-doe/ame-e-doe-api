package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.dto.CardDTO;
import br.com.loveanddonateapi.mapper.CardMapper;
import br.com.loveanddonateapi.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RestController
@RequiredArgsConstructor
@RequestMapping( path = "/api/card" )
@Api( tags = { "Cartões" } )
public class CardController {

    private final CardService cardService;

    @ApiOperation( value = "Cadastra um novo cartão" )
    @PostMapping( "/create" )
    @ResponseStatus( HttpStatus.CREATED )
    public ResponseEntity< CardDTO > create( @RequestHeader( "idUser" ) Long idUser,
                                             @Valid @RequestBody CardDTO cardDTO ) {
        return new ResponseEntity<>( cardService.save(
                CardMapper.cardDtoToEntity( cardDTO ),
                idUser ),
                HttpStatus.CREATED );
    }

    @ApiOperation( value = "Consulta pelo identificador do cartão" )
    @GetMapping( "/{idCard}" )
    public ResponseEntity< CardDTO > getById( @PathVariable Long idCard ) {
        return ResponseEntity
                .ok( cardService.getByIdCard( idCard ) );
    }

    @ApiOperation( value = "Lista todos cartões pelo id de um usuario" )
    @GetMapping( "/list" )
    public ResponseEntity< List< CardDTO > > getAllCards( @RequestHeader( "idUser" ) Long idUser ) {
        return ResponseEntity
                .ok( cardService.getAll( idUser ) );
    }

    @ApiOperation( value = "Delete pelo identificado do cartão" )
    @DeleteMapping( "/{idCard}" )
    public ResponseEntity< ? > deleteCard( @PathVariable Long idCard ) {
        cardService.delete( idCard );
        return ResponseEntity
                .noContent()
                .build();
    }

}