package br.com.loveanddonateapi.dto;

import br.com.loveanddonateapi.models.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartDTO {

    private List< CartItem > cartItems;
    private Double total;

}
