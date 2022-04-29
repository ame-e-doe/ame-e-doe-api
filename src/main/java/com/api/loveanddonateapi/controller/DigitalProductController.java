package com.api.loveanddonateapi.controller;

import com.api.loveanddonateapi.dto.DigitalProductDTO;
import com.api.loveanddonateapi.dto.DigitalProductsByUserDTO;
import com.api.loveanddonateapi.service.DigitalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api/v1/products" )
public class DigitalProductController {

    @Autowired
    private DigitalProductService digitalProductService;

    @GetMapping( "/list" )
    public ResponseEntity< List< DigitalProductDTO > > getAllProducts() {
        return ResponseEntity.ok( digitalProductService.getAllProducts() );
    }

    @GetMapping( "/{idProduct}" )
    public ResponseEntity< DigitalProductDTO > getProductById( @PathVariable Long idProduct ) {
        return ResponseEntity.ok( digitalProductService.getProductById( idProduct ) );
    }

    @GetMapping( "/list/category/{idCategory}" )
    public ResponseEntity< List< DigitalProductDTO > > getAllProductsByCategory( @PathVariable Long idCategory ) {
        return ResponseEntity.ok( digitalProductService.getAllProductsByCategory( idCategory ) );
    }

    @GetMapping( "list/user/{idUser}" )
    public ResponseEntity< DigitalProductsByUserDTO > getAllProductsByUser( @PathVariable Long idUser ) {
        return ResponseEntity.ok( digitalProductService.getAllProductsByUser( idUser ) );
    }

    @GetMapping( "/list/search" )
    public ResponseEntity< List< DigitalProductDTO > > getAllProductsBySearch( @RequestParam String text ) {
        return ResponseEntity.ok( digitalProductService.getAllProductsBySearch( text ) );
    }

}
