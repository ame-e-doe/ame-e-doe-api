package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.dto.response.DigitalProductResponseDTO;
import br.com.loveanddonateapi.service.DigitalProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
@Api(tags = {"Produtos"})
public class DigitalProductController {

    @Autowired
    private DigitalProductService digitalProductService;

    @ApiOperation(value = "Lista todos os produtos")
    @GetMapping("/list")
    public ResponseEntity<List<DigitalProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(digitalProductService.getAll());
    }

    @ApiOperation(value = "Consulta produto pelo identificador")
    @GetMapping("/{idProduct}")
    public ResponseEntity<DigitalProductResponseDTO> getProductById(@PathVariable Long idProduct) {
        return ResponseEntity.ok(digitalProductService.getById(idProduct));
    }

    @ApiOperation(value = "Consulta produto pelo identificador da categoria")
    @GetMapping("/list/category/{idCategory}")
    public ResponseEntity<List<DigitalProductResponseDTO>> getAllProductsByCategory(@PathVariable Long idCategory) {
        return ResponseEntity.ok(digitalProductService.getAllProductsByCategory(idCategory));
    }

    @ApiOperation(value = "Lista produtos pela busca")
    @GetMapping("/list/search")
    public ResponseEntity<List<DigitalProductResponseDTO>> getAllProductsBySearch(@RequestParam String text) {
        return ResponseEntity.ok(digitalProductService.getAllProductsBySearch(text));
    }

}
