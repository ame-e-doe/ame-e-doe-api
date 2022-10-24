package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.models.Category;
import br.com.loveanddonateapi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category getById( Long id ) {
        return categoryRepository.findById( id )
                .orElseThrow( () -> new EntityNotFoundException(
                        String.format( "Categoria com identificador [%d] não encontrado." ) ) );
    }

    public List< Category > getAll() {
        return categoryRepository.findAll();
    }
}
