package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.dto.CartDTO;
import br.com.loveanddonateapi.mapper.CartMapper;
import br.com.loveanddonateapi.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RestController
@RequiredArgsConstructor
@RequestMapping( path = "/api/cart" )
@Api( tags = { "Carrinho de compras" } )
public class CartController {

    private final CartService cartService;

    @ApiOperation( value = "Adiciona um produto ao carrinho de compras." )
    @PutMapping( "/add-product/{idProduct}" )
    public ResponseEntity< CartDTO > addCartItem( @RequestHeader( "idUser" ) Long idUser,
                                               @PathVariable Long idProduct ) {
        return new ResponseEntity<>( CartMapper.cartEntityToCartDTO(
                cartService.addCartItem( idUser, idProduct ) ),
                HttpStatus.OK );
    }

    @ApiOperation( value = "Recupera o carrinho de compras do usuario logado." )
    @GetMapping()
    public ResponseEntity< CartDTO > getCart( @RequestHeader( "idUser" ) Long idUser ) {
        return new ResponseEntity<>( CartMapper
                .cartEntityToCartDTO( cartService.getCartByUser( idUser ) ),
                HttpStatus.OK );
    }

    @ApiOperation( value = "Remove um item do carrinho de compras." )
    @DeleteMapping( "/{cartItemId}" )
    public ResponseEntity< ? > removeCartItem( @RequestHeader( "idUser" ) Long idUser,
                                                  @PathVariable Long cartItemId ) {
        return new ResponseEntity<>( cartService
                .removeCartItem( idUser, cartItemId ),
                HttpStatus.NO_CONTENT );
    }
}
