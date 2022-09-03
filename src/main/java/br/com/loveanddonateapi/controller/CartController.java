package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/cart/")
@Api(tags = {"Carrinho de compras"})
public class CartController {

    @Autowired
    private CartService cartService;

    @ApiOperation(value = "Adicionar um produto ao carrinho de compras.")
    @PostMapping("add-product/{productId}")
    public ResponseEntity<Void> addCartItem(@RequestHeader("Authorization") String token, @PathVariable Long productId) {
        this.cartService.addCartItem(token, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
