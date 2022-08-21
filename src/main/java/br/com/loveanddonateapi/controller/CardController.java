package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.dto.CardDTO;
import br.com.loveanddonateapi.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/card/")
@Api(tags = {"Cartões"})
public class CardController {

    @Autowired
    CardService cardService;

    @ApiOperation(value = "Cadastra um novo cartão")
    @PostMapping("create/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CardDTO> create(@RequestHeader("Authorization") String token, @Valid @RequestBody CardDTO cardDTO) {
        return ResponseEntity.ok(cardService.create(cardDTO, token));
    }

    @ApiOperation(value = "Consulta cartão pelo identificador")
    @GetMapping("{cardId}")
    public ResponseEntity<CardDTO> getById(@PathVariable Long cardId) {
        return ResponseEntity.ok(cardService.getById(cardId));
    }

    @ApiOperation(value = "Lista todos cartões de um usuario")
    @GetMapping("list")
    public ResponseEntity<List<CardDTO>> getAllCards(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(cardService.getAll(token));
    }

    @ApiOperation(value = "Deleta um cartão pelo identificador")
    @DeleteMapping("delete/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long cardId) {
        this.cardService.delete(cardId);
        return ResponseEntity.ok().build();
    }

}
