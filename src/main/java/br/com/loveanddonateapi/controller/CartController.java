package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.models.Cart;
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

    @ApiOperation(value = "Adiciona um produto ao carrinho de compras.")
    @PostMapping("add-product/{productId}")
    public ResponseEntity<Cart> addCartItem(@RequestHeader("Authorization") String token, @PathVariable Long productId) {
        return new ResponseEntity<>(this.cartService.addCartItem(token, productId), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Recupera o carrinho de compras do usuario logado.")
    @GetMapping()
    public ResponseEntity<Cart> getCartByUser(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(this.cartService.getCartByUser(token), HttpStatus.OK);
    }

    @ApiOperation(value = "Remove um item do carrinho de compras.")
    @DeleteMapping("{cartItemId}")
    public ResponseEntity<Cart> removeCartItem(@RequestHeader("Authorization") String token, @PathVariable Long cartItemId) {
        return new ResponseEntity<>(this.cartService.removeCartItem(token, cartItemId), HttpStatus.NO_CONTENT);
    }
}
