package com.api.loveanddonateapi.controller;

import com.api.loveanddonateapi.dto.DigitalProductDto;
import com.api.loveanddonateapi.dto.DigitalProductsByUser;
import com.api.loveanddonateapi.service.DigitalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/v1/products" )
public class DigitalProductController {

    @Autowired
    private DigitalProductService digitalProductService;

    @GetMapping( "/list" )
    public ResponseEntity< List< DigitalProductDto > > getAllProducts() {
        return ResponseEntity.ok( digitalProductService.getAllProducts() );
    }

    @GetMapping( "/{idProduct}" )
    public ResponseEntity< DigitalProductDto > getProductById( @PathVariable Long idProduct ) {
        return ResponseEntity.ok( digitalProductService.getProductById( idProduct ) );
    }

    @GetMapping( "/list/category/{idCategory}" )
    public ResponseEntity< List< DigitalProductDto > > getAllProductsByCategory( @PathVariable Long idCategory ) {
        return ResponseEntity.ok( digitalProductService.getAllProductsByCategory( idCategory ) );
    }

    @GetMapping( "list/user/{idUser}" )
    public ResponseEntity< DigitalProductsByUser > getAllProductsByUser( @PathVariable Long idUser ) {
        return ResponseEntity.ok( digitalProductService.getAllProductsByUser( idUser ) );
    }

    @GetMapping( "/list/search" )
    public ResponseEntity< List< DigitalProductDto > > getAllProductsBySearch( @RequestParam String text ) {
        return ResponseEntity.ok( digitalProductService.getAllProductsBySearch( text ) );
    }

}
