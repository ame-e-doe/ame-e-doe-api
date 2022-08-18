package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.dto.DigitalProductDTO;
import br.com.loveanddonateapi.service.DigitalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products/")
public class DigitalProductController {

    @Autowired
    private DigitalProductService digitalProductService;

    @GetMapping("list")
    public ResponseEntity<List<DigitalProductDTO>> getAllProducts() {
        return ResponseEntity.ok(digitalProductService.getAll());
    }

    @GetMapping("{idProduct}")
    public ResponseEntity<DigitalProductDTO> getProductById(@PathVariable Long idProduct) {
        return ResponseEntity.ok(digitalProductService.getById(idProduct));
    }

    @GetMapping("list/category/{idCategory}")
    public ResponseEntity<List<DigitalProductDTO>> getAllProductsByCategory(@PathVariable Long idCategory) {
        return ResponseEntity.ok(digitalProductService.getAllProductsByCategory(idCategory));
    }

    @GetMapping("list/search")
    public ResponseEntity<List<DigitalProductDTO>> getAllProductsBySearch(@RequestParam String text) {
        return ResponseEntity.ok(digitalProductService.getAllProductsBySearch(text));
    }

    /* @GetMapping( "list/user/{idUser}" )
    public ResponseEntity< DigitalProductsByUserDTO > getAllProductsByUser( @PathVariable Long idUser ) {
        return ResponseEntity.ok( digitalProductService.getAllProductsByUser( idUser ) );
    }*/

}
