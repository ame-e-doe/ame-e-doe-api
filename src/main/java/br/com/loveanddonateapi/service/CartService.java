package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.models.Cart;
import br.com.loveanddonateapi.models.CartItem;
import br.com.loveanddonateapi.models.DigitalProduct;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.repository.CartItemRepository;
import br.com.loveanddonateapi.repository.CartRepository;
import br.com.loveanddonateapi.repository.DigitalProductRepository;
import br.com.loveanddonateapi.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Autowired
    DigitalProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    public void addCartItem(String token, Long productId) {
        Long userId = Long.parseLong(jwtUtils.getUserFromJwtToken(token));
        User user = userService.getById(userId);
        Cart cart = cartRepository.getCartByUserId(user.getId());
        List<CartItem> itens = cart.getCartItem();
        DigitalProduct product = productRepository.getById(productId);

        if (!itens.isEmpty()) {
            for (CartItem item : itens) {
                if (product.getId().equals(item.getProduct().getId())) {
                    item.setQuantity(item.getQuantity() + 1);
                    item.setPrice(item.getQuantity() * item.getProduct().getValue());
                    cart.setTotalPrice(item.getPrice());
                    cartItemRepository.save(item);
                } else {
                    CartItem cartItem = new CartItem();
                    cartItem.setQuantity(1);
                    cartItem.setProduct(product);
                    cartItem.setPrice(product.getValue() * 1);
                    cartItem.setCart(cart);
                    cart.setTotalPrice(cart.getTotalPrice() + product.getValue());
                    cartItemRepository.save(cartItem);
                }
            }
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(1);
            cartItem.setProduct(product);
            cartItem.setPrice(product.getValue() * 1);
            cartItem.setCart(cart);
            cart.setTotalPrice(product.getValue());
            cartItemRepository.save(cartItem);
        }
    }

}
