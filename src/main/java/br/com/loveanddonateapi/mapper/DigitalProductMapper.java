package br.com.loveanddonateapi.mapper;

import br.com.loveanddonateapi.dto.response.DigitalProductResponseDTO;
import br.com.loveanddonateapi.models.DigitalProduct;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class DigitalProductMapper {

    public static DigitalProduct digitalProductDTOToEntity( DigitalProductResponseDTO dto ) {
        return DigitalProduct.builder()
                .id( dto.getId() )
                .image( dto.getImage() )
                .title( dto.getTitle() )
                .description( dto.getDescription() )
                .value( dto.getValue() )
                .category( dto.getCategory() )
                .build();
    }

    public static DigitalProductResponseDTO digitalProductEntityToDTO( DigitalProduct digitalProduct ) {
        return DigitalProductResponseDTO.builder()
                .id( digitalProduct.getId() )
                .image( digitalProduct.getImage() )
                .title( digitalProduct.getTitle() )
                .description( digitalProduct.getDescription() )
                .value( digitalProduct.getValue() )
                .category( digitalProduct.getCategory() )
                .build();
    }

}