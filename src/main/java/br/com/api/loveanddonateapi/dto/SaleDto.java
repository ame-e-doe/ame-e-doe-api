package br.com.api.loveanddonateapi.dto;

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
public class SaleDto {

    private Long id;

    private double value;

    private Set<DigitalProductDTO> products = new HashSet<>();

}
