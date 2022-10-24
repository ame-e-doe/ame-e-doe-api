package br.com.loveanddonateapi.dto.response;

import br.com.loveanddonateapi.models.Category;
import br.com.loveanddonateapi.models.DigitalProduct;
import br.com.loveanddonateapi.models.Image;
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

    private Image image;

    private String title;

    private String description;

    private Double value;

    private Category category;

    public DigitalProductResponseDTO(DigitalProduct entity) {
        this.setId(entity.getId());
        this.setImage(entity.getImage());
        this.setTitle(entity.getTitle());
        this.setDescription(entity.getDescription());
        this.setValue(entity.getValue());
        this.setCategory(entity.getCategory());
    }

}
