package br.com.loveanddonateapi.dto.response;

import br.com.loveanddonateapi.models.Sale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleResponseDTO {

    private Long id;

    private double value;

    private Set< DigitalProductResponseDTO > products = new HashSet<>();

    public SaleResponseDTO(Sale entity) {
        this.setId(entity.getId());
        this.setValue(entity.getValue());
        this.setProducts(entity.getProducts()
                .stream()
                .map(product -> new DigitalProductResponseDTO(product))
                .collect(Collectors.toSet()));
    }

}
