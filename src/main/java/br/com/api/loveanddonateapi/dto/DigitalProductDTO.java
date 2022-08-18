package br.com.api.loveanddonateapi.dto;

import br.com.api.loveanddonateapi.models.Category;
import br.com.api.loveanddonateapi.models.DigitalProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DigitalProductDTO {

    private Long id;

    private String url;

    private String title;

    private String description;

    private Double value;

    private Category category;

    public DigitalProductDTO(DigitalProduct entity) {
        this.setId(entity.getId());
        this.setUrl(entity.getUrl());
        this.setTitle(entity.getTitle());
        this.setDescription(entity.getDescription());
        this.setValue(entity.getValue());
        this.setCategory(entity.getCategory());
    }

}
