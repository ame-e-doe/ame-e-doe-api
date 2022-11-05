package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.mapper.DigitalProductMapper;
import br.com.loveanddonateapi.models.Cart;
import br.com.loveanddonateapi.models.CartItem;
import br.com.loveanddonateapi.models.DigitalProduct;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.repository.CartItemRepository;
import br.com.loveanddonateapi.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CartService {

    @Autowired
    private DigitalProductService digitalProductService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart addCartItem( Long idUser, Long idProduct ) {

        log.info( "find card by id user {}", idUser );
        Cart cart = cartRepository.getCartByUserId( idUser );

        if( Objects.isNull( cart ) ) {
            log.error( "error in cart localize" );
            throw new EntityNotFoundException( "Carrinho não localizado" );
        }
        log.info( "cart return" );
        return includeItem( cart, idProduct );

    }

    private Cart includeItem( Cart cart, Long idProduct ) {
        List< CartItem > itens = cart.getListCartItens();

        log.info( "find product by id {}", idProduct );
        DigitalProduct digitalProduct = DigitalProductMapper
                .digitalProductDTOToEntity( digitalProductService.getById( idProduct ) );

        CartItem cartItem = new CartItem();

        if( itens.isEmpty() ) {
            cartItem.setQuantity( 1 );
            cartItem.setProduct( digitalProduct );
            cartItem.setPrice( digitalProduct.getValue() * 1 );
            cart.setTotalPrice( digitalProduct.getValue() );
        } else {
            Optional< CartItem > item = itens.stream().filter( ci -> ci.getProduct().getId().equals( digitalProduct.getId() ) ).findFirst();
            if( item.isPresent() ) {
                cartItem = item.get();
                cartItem.setQuantity( cartItem.getQuantity() + 1 );
                cartItem.setPrice( cartItem.getQuantity() * cartItem.getProduct().getValue() );
            } else {
                cartItem.setQuantity( 1 );
                cartItem.setProduct( digitalProduct );
                cartItem.setPrice( digitalProduct.getValue() * 1 );
            }
            cart.setTotalPrice( cart.getTotalPrice() + digitalProduct.getValue() );
        }
        cartItem.setCart( cart );
        log.info( "save cartItem in database" );
        cartItemRepository.save( cartItem );
        return cart;
    }

    public Cart removeCartItem( Long idUser, Long cartItemId ) {

        log.info( "find cart by id user {}", idUser );
        Cart cart = cartRepository.getCartByUserId( idUser );

        List< CartItem > itens = cart.getListCartItens();

        Optional< CartItem > ci = itens.stream().filter( cartItem -> cartItem.getId().equals( cartItemId ) ).findFirst();

        if( ci.isPresent() ) {
            CartItem cartItem = ci.get();
            if( cartItem.getQuantity() > 1 ) {
                cartItem.setQuantity( cartItem.getQuantity() - 1 );
                cartItem.setPrice( cartItem.getPrice() - cartItem.getProduct().getValue() );
                cart.setTotalPrice( cart.getTotalPrice() - cartItem.getProduct().getValue() );
                cartItem.setCart( cart );
                log.info( "save cartItem in database" );
                cartItemRepository.save( cartItem );
            } else {
                cart.setTotalPrice( cart.getTotalPrice() - cartItem.getProduct().getValue() );
                cartItem.setCart( cart );
                itens.remove( cartItem );

                log.info( "save cartItem in database" );
                cartRepository.save( cart );

                log.info( "delete cartItem in database" );
                cartItemRepository.delete( cartItem );
            }
        }
        return cart;
    }

    public Cart getCartByUser( Long idUser ) {
        log.info( "get cart by id user {}", idUser );
        Cart cart = cartRepository.getCartByUserId( idUser );
        return cart;
    }

    public void createCart( User user ) {
        log.info( "create cart for user {} in database", user.getUsername() );
        cartRepository.save( new Cart( user ) );
    }
}
