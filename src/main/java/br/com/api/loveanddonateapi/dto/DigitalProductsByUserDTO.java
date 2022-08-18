package br.com.api.loveanddonateapi.dto;

import br.com.api.loveanddonateapi.models.DigitalProduct;
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
public class DigitalProductsByUserDTO {

    private Long id;
    private String name;
    private String email;

    private Set< DigitalProduct > products = new HashSet<>();

}
