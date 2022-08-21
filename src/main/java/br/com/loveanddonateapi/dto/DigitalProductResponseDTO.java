package br.com.loveanddonateapi.dto;

import br.com.loveanddonateapi.models.Category;
import br.com.loveanddonateapi.models.DigitalProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DigitalProductResponseDTO {

    private Long id;

    private String url;

    private String title;

    private String description;

    private Double value;

    private Category category;

    public DigitalProductResponseDTO(DigitalProduct entity) {
        this.setId(entity.getId());
        this.setUrl(entity.getUrl());
        this.setTitle(entity.getTitle());
        this.setDescription(entity.getDescription());
        this.setValue(entity.getValue());
        this.setCategory(entity.getCategory());
    }

}
