package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.models.Cart;
import br.com.loveanddonateapi.models.CartItem;
import br.com.loveanddonateapi.models.DigitalProduct;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.repository.CartItemRepository;
import br.com.loveanddonateapi.repository.CartRepository;
import br.com.loveanddonateapi.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Autowired
    DigitalProductService productService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    public Cart addCartItem(String token, Long productId) {
        Cart cart = this.getCartByUser(token);
        List<CartItem> itens = cart.getCartItem();
        DigitalProduct product = productService.findById(productId);
        CartItem cartItem = new CartItem();

        if (itens.isEmpty()) {
            cartItem.setQuantity(1);
            cartItem.setProduct(product);
            cartItem.setPrice(product.getValue() * 1);
            cart.setTotalPrice(product.getValue());
        } else {
            Optional<CartItem> item = itens.stream().filter(ci -> ci.getProduct().getId().equals(product.getId())).findFirst();
            if (item.isPresent()) {
                cartItem = item.get();
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItem.setPrice(cartItem.getQuantity() * cartItem.getProduct().getValue());
            } else {
                cartItem.setQuantity(1);
                cartItem.setProduct(product);
                cartItem.setPrice(product.getValue() * 1);
            }
            cart.setTotalPrice(cart.getTotalPrice() + product.getValue());
        }
        cartItem.setCart(cart);
        return cartItemRepository.save(cartItem).getCart();
    }

    public Cart removeCartItem(String token, Long cartItemId) {
        Cart cart = this.getCartByUser(token);
        List<CartItem> itens = cart.getCartItem();

        Optional<CartItem> ci = itens.stream().filter(cartItem -> cartItem.getId().equals(cartItemId)).findFirst();

        if (ci.isPresent()) {
            CartItem cartItem = ci.get();
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartItem.setPrice(cartItem.getPrice() - cartItem.getProduct().getValue());
                cart.setTotalPrice(cart.getTotalPrice() - cartItem.getProduct().getValue());
                cartItem.setCart(cart);
                cartItemRepository.save(cartItem);
            } else {
                cart.setTotalPrice(cart.getTotalPrice() - cartItem.getProduct().getValue());
                cartItem.setCart(cart);
                itens.remove(cartItem);
                cartRepository.save(cart);
                cartItemRepository.delete(cartItem);
            }
        }
        return cart;
    }

    public Cart getCartByUser(String token) {
        Long userId = Long.parseLong(jwtUtils.getUserFromJwtToken(token));
        User user = userService.getById(userId);
        return cartRepository.getCartByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Carrinho do usuario não encontrado."));
    }

    public void createCart(User user) {
        cartRepository.save(new Cart(null, user, null, 0.0));
    }
}
