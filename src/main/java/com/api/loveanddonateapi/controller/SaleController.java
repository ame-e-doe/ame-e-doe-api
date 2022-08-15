package com.api.loveanddonateapi.controller;

import com.api.loveanddonateapi.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( origins = "*", maxAge = 3600)
@RestController
@RequestMapping( "/api/sales" )
public class SaleController {

    @Autowired
    private SaleService saleService;

    /*@PostMapping( "create/{userId}" )
    public ResponseEntity< SaleDto > createSale(
            @Valid @RequestBody SaleDto saleDto,
            @PathVariable Long userId ) {
        return ResponseEntity.ok( this.saleService.createSale( saleDto, userId ) );
    }

    @GetMapping("/list/{userId}")
    @PreAuthorize( "hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')" )
    public ResponseEntity< List<SaleDto> > getAllSales(@PathVariable Long userId) {
        return ResponseEntity.ok( this.saleService.getAllSales(userId) );
    }

    @GetMapping("/{saleId}")
    public ResponseEntity< SaleDto > getSaleById(@PathVariable Long saleId) {
        return ResponseEntity.ok( this.saleService.getSaleById(saleId) );
    }*/

}
