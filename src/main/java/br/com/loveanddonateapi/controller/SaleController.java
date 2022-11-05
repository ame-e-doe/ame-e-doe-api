package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.dto.SaleCreateDTO;
import br.com.loveanddonateapi.dto.response.SaleResponseDTO;
import br.com.loveanddonateapi.service.SaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sales")
@Api(tags = {"Pedidos"})
public class SaleController {

    @Autowired
    private SaleService saleService;

    @ApiOperation(value = "Cadastra uma nova venda")
    @PostMapping("/create")
    public ResponseEntity<SaleResponseDTO> create(@Valid @RequestBody SaleCreateDTO createSaleDto, @RequestHeader( "idUser" ) Long idUser) {
        return ResponseEntity.ok(this.saleService.createSale(createSaleDto, idUser));
    }

    @ApiOperation(value = "Lista todas os pedidos de um usuario")
    @GetMapping("/list")
    public ResponseEntity<List<SaleResponseDTO>> getAllSales(@RequestHeader( "idUser" ) Long idUser) {
        return ResponseEntity.ok(this.saleService.getAll(idUser));
    }

    @ApiOperation(value = "Consulta uma venda pelo identificador")
    @GetMapping("/{saleId}")
    public ResponseEntity<SaleResponseDTO> getSaleById(@PathVariable Long saleId) {
        return ResponseEntity.ok(this.saleService.getById(saleId));
    }

}
