package br.com.loveanddonateapi.mapper;

import br.com.loveanddonateapi.dto.CartDTO;
import br.com.loveanddonateapi.models.Cart;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class CartMapper {

    public static CartDTO cartEntityToCartDTO( Cart cart ) {
        return CartDTO.builder()
                .cartItems( cart.getListCartItens() )
                .total( cart.getTotalPrice() )
                .build();
    }

}
