package br.com.loveanddonateapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DigitalProductCreateDTO {

    private Long id;

    private Long imageId;

    private String title;

    private String description;

    private Double value;

    private Long categoryId;

}
