package com.api.loveanddonateapi.controller;

import com.api.loveanddonateapi.dto.CardDTO;
import com.api.loveanddonateapi.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping( path = "api/v1/card" )
public class CardController {

    @Autowired
    CardService cardService;

    @PostMapping( "/create/{userId}" )
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
        this.cardService.deleteCardById( cardId );
        return ResponseEntity.ok().build();
    }

}
