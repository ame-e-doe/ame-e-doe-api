package br.com.loveanddonateapi.dto;

import br.com.loveanddonateapi.models.DigitalProduct;
import br.com.loveanddonateapi.models.Sale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleCreateDTO {

    private Long id;

    private double value;

    private Set<Long> products = new HashSet<>();

    public Sale asEntity(SaleCreateDTO dto) {
        Sale sale = new Sale();
        sale.setValue(dto.getValue());
        Set<Long> idProducts = dto.getProducts();

        for (Long idProduct : idProducts) {
            DigitalProduct p = new DigitalProduct();
            p.setId(idProduct);
            sale.getProducts().add(p);
        }

        return sale;
    }
}
