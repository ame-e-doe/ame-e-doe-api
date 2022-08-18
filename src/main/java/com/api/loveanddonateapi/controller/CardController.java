package com.api.loveanddonateapi.controller;

import com.api.loveanddonateapi.dto.CardDTO;
import com.api.loveanddonateapi.models.Card;
import com.api.loveanddonateapi.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api( value = "Card" )
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/card/")
public class CardController {

    @Autowired
    CardService cardService;

    @ApiOperation( value = "Cadastro de cartão" )
    @PostMapping("create/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Card> createOrUpdate(@PathVariable("userId") Long userId,
                                               @Valid @RequestBody CardDTO cardDTO) {
        return ResponseEntity.ok(cardService.createOrUpdate(cardDTO));
    }

    @ApiOperation( value = "Consulta cartão")
    @GetMapping("get/{cardId}")
    public ResponseEntity<CardDTO> getById(@PathVariable Long cardId) {
        return ResponseEntity.ok(cardService.getById(cardId));
    }

    @ApiOperation( value = "Lista todos cartões")
    @GetMapping("list/{userId}")
    public ResponseEntity<List<CardDTO>> getAllCards(@PathVariable Long userId) {
        return ResponseEntity.ok(cardService.getAll());
    }

    @ApiOperation( value = "Deleta cartão" )
    @DeleteMapping("delete/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long cardId) {
        this.cardService.delete(cardId);
        return ResponseEntity.ok().build();
    }

}
