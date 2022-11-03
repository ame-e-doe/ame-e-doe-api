package br.com.loveanddonateapi.unit.service;

import br.com.loveanddonateapi.dto.CategoryDTO;
import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.models.Category;
import br.com.loveanddonateapi.repository.CategoryRepository;
import br.com.loveanddonateapi.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles( "test" )
public class CategoryServiceTest {

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @Test
    @DisplayName( "deve consultar categoria por id com sucesso" )
    public void deveConsultarCategoriaComSucesso() {

        Category category = createValidCategoryWithIdAndDesc( 1L, "Imagens" );

        when( categoryRepository.findAllById( category.getId() ) )
                .thenReturn( category );

        CategoryDTO categoryDTO = categoryService.getById( category.getId() );

        assertThat( categoryDTO ).isNotNull();
        assertThat( categoryDTO.getDescription() ).isEqualTo( category.getDescription() );
        assertThat( categoryDTO.getId() ).isEqualTo( category.getId() );

    }

    @Test
    @DisplayName( "deve consultar categoria por id invalido" )
    public void deveConsultarPorIdInvalido() throws EntityNotFoundException {

        Long idInvalido = 1L;

        Throwable t = assertThrows( EntityNotFoundException.class,
                () -> categoryService.getById( idInvalido ) );

        assertTrue( t.getMessage().contains( "Categoria não localizada." ) );

    }

    @Test
    @DisplayName( "deve listar todas as categorias" )
    public void deveListarTodasCategorias() {

        Category category1 = createValidCategoryWithIdAndDesc( 1L, "Imagens" );
        Category category2 = createValidCategoryWithIdAndDesc( 2L, "Videos" );

        List< Category > categoryList = new ArrayList<>();

        categoryList.add( category1 );
        categoryList.add( category2 );

        when( categoryRepository.findAll() ).thenReturn( categoryList );

        List< CategoryDTO > categoryDTOList = categoryService.getAll();

        assertThat( categoryDTOList ).isNotNull();

    }

    @Test
    @DisplayName( "deve lancar erro ao nao encontrar categorias" )
    public void deveLancarErroAoNaoEncontrarCategorias() throws EntityNotFoundException {

        Throwable t = assertThrows( EntityNotFoundException.class, () -> categoryService.getAll() );

        assertTrue( t.getMessage().contains( "Nenhuma categoria encontrada." ) );

    }

    private Category createValidCategoryWithIdAndDesc( Long id, String desc ) {
        return Category.builder()
                .id( id )
                .description( desc )
                .build();
    }

}