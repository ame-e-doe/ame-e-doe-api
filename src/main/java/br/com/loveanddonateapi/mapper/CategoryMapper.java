package br.com.loveanddonateapi.mapper;

import br.com.loveanddonateapi.dto.CategoryDTO;
import br.com.loveanddonateapi.models.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class CategoryMapper {

    public static CategoryDTO categoryEntityToDTO(  Category category ) {
        return CategoryDTO.builder()
                .id( category.getId() )
                .description( category.getDescription() )
                .build();
    }

    public static Category categoryDTOToEntity( CategoryDTO categoryDTO ) {
        return Category.builder()
                .id( categoryDTO.getId() )
                .description( categoryDTO.getDescription() )
                .build();
    }

}
