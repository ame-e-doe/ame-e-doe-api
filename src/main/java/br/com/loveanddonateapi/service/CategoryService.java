package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.dto.CategoryDTO;
import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.mapper.CategoryMapper;
import br.com.loveanddonateapi.models.Category;
import br.com.loveanddonateapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO getById( Long id ) throws EntityNotFoundException {

        Category category = categoryRepository.findByCategoryId( id );

        if( Objects.isNull( category ) ) {
            throw new EntityNotFoundException( "Categoria não localizada." );
        }

        return CategoryMapper.categoryEntityToDTO( category );

    }

    public List< CategoryDTO > getAll() throws EntityNotFoundException {

        List< Category > categoryList = categoryRepository.findAll();

        if( Objects.isNull( categoryList ) || categoryList.isEmpty() ) {
            throw new EntityNotFoundException( "Nenhuma categoria encontrada." );
        }

        List< CategoryDTO > categoryDTOList = categoryList
                .stream()
                .map( c -> CategoryMapper.categoryEntityToDTO( c ) )
                .collect( Collectors.toList() );

        return categoryDTOList;
    }
}
