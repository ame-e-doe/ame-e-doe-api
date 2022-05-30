package com.api.loveanddonateapi.controller;

import com.api.loveanddonateapi.dto.CardDTO;
import com.api.loveanddonateapi.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin( origins = "*", maxAge = 3600)
@RestController
@RequestMapping( path = "/api/card" )
public class CardController {

    @Autowired
    CardService cardService;

    @PostMapping( "/create/{userId}" )
    @ResponseStatus( HttpStatus.CREATED )
    public ResponseEntity< CardDTO > createCard( @PathVariable( "userId" ) Long userId,
                              @Valid @RequestBody CardDTO cardDTO ) {
        return ResponseEntity.ok( cardService.createCard( cardDTO, userId ) );
    }

    @GetMapping( "/list/{userId}" )
    public ResponseEntity< ? > getAllCards( @PathVariable Long userId ) {
        return cardService.getCard( userId );
    }

    @DeleteMapping( "delete/{cardId}" )
    public ResponseEntity< Void > deleteCard( @PathVariable Long cardId ) {
        this.cardService.deleteCardById( cardId );
        return ResponseEntity.ok().build();
    }

}
