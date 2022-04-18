package com.api.loveanddonateapi.controller;

import com.api.loveanddonateapi.dto.CardDTO;
import com.api.loveanddonateapi.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping( path = "api/v1/card" )
public class CardController {

    private final CardService cardService;

    public CardController( CardService cardService ) {
        this.cardService = cardService;
    }

    @PostMapping( "/{userId}" )
    public ResponseEntity< CardDTO > createCard(
            @Valid @RequestBody CardDTO cardDto,
            @PathVariable Long userId ) {
        return ResponseEntity.ok( this.cardService.createCard( cardDto, userId ) );
    }

    @GetMapping( "/list/{userId}" )
    public ResponseEntity< List< CardDTO > > getAllCards( @PathVariable Long userId ) {
        return ResponseEntity.ok( this.cardService.getAllCards( userId ) );
    }

    @DeleteMapping( "delete/{cardId}" )
    public ResponseEntity< Void > deleteCard( @PathVariable Long cardId ) {
        this.cardService.deleteCard( cardId );
        return ResponseEntity.noContent().build();
    }

}
