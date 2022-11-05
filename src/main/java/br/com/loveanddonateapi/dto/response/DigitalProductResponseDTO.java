package br.com.loveanddonateapi.dto.response;

import br.com.loveanddonateapi.models.Category;
import br.com.loveanddonateapi.models.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DigitalProductResponseDTO {

    private Long id;

    private Image image;

    private String title;

    private String description;

    private Double value;

    private Category category;

}