package com.api.loveanddonateapi.controller;

import com.api.loveanddonateapi.dto.DigitalProductDto;
import com.api.loveanddonateapi.dto.DigitalProductsByUser;
import com.api.loveanddonateapi.service.DigitalProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/v1/products" )
public class DigitalProductController {

    private final DigitalProductService dpService;

    public DigitalProductController( DigitalProductService dpService ) {
        this.dpService = dpService;
    }

    @GetMapping( "/list" )
    public ResponseEntity< List< DigitalProductDto > > getAllProducts() {
        return ResponseEntity.ok( this.dpService.getAllProducts() );
    }

    @GetMapping( "/{idProduct}" )
    public ResponseEntity< DigitalProductDto > getProductById( @PathVariable Long idProduct ) {
        return ResponseEntity.ok( this.dpService.getProductById( idProduct ) );
    }

    @GetMapping( "/list/category/{idCategory}" )
    public ResponseEntity< List< DigitalProductDto > > getAllProductsByCategory( @PathVariable Long idCategory ) {
        return ResponseEntity.ok( this.dpService.getAllProductsByCategory( idCategory ) );
    }

    @GetMapping( "list/user/{idUser}" )
    public ResponseEntity< DigitalProductsByUser > getAllProductsByUser( @PathVariable Long idUser ) {
        return ResponseEntity.ok( this.dpService.getAllProductsByUser( idUser ) );
    }

    @GetMapping( "/list/search" )
    public ResponseEntity< List< DigitalProductDto > > getAllProductsBySearch( @RequestParam String text ) {
        return ResponseEntity.ok( this.dpService.getAllProductsBySearch( text ) );
    }

}
