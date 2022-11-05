package br.com.loveanddonateapi.unit.service;

import br.com.loveanddonateapi.dto.CategoryDTO;
import br.com.loveanddonateapi.dto.response.DigitalProductResponseDTO;
import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.models.Category;
import br.com.loveanddonateapi.models.DigitalProduct;
import br.com.loveanddonateapi.models.Image;
import br.com.loveanddonateapi.repository.CategoryRepository;
import br.com.loveanddonateapi.repository.DigitalProductRepository;
import br.com.loveanddonateapi.service.CategoryService;
import br.com.loveanddonateapi.service.DigitalProductService;
import br.com.loveanddonateapi.unit.utils.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles( "test" )
public class DigitalProductServiceTest {

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    CategoryService categoryService;

    @MockBean
    DigitalProductRepository digitalProductRepository;

    @Autowired
    DigitalProductService digitalProductService;

    private Category category;
    private Image image;

    @BeforeEach
    public void setUp() {

        category = DataUtils.createValidCategoryWithIdAndDesc( 1L, "Plantas" );
        image = DataUtils.createValidImageWithData();

    }

    @Test
    @DisplayName( "deve listar produtos pela categoria" )
    public void deveListarProdutosPelaCategoria() {

        List<DigitalProduct> digitalProductList = new ArrayList<>();

        when( categoryService.getById( category.getId() ) ).thenReturn(
                CategoryDTO.builder()
                        .id( category.getId() )
                        .description( category.getDescription() )
                        .build() );

        DigitalProduct digitalProduct = createValidDigitalProductWithData( 1L, image, category );

        digitalProductList.add( digitalProduct );

        when( digitalProductRepository.getDigitalProductsByCategory( category ) )
                .thenReturn( digitalProductList );

        List< DigitalProductResponseDTO > digitalProductResponseDTOList = digitalProductService
                .getAllProductsByCategory( category.getId() );

        assertThat( digitalProductResponseDTOList ).isNotNull();
        assertThat( digitalProductResponseDTOList.get( 0 ).getValue() ).isEqualTo( digitalProduct.getValue() );
        assertThat( digitalProductResponseDTOList.get( 0 ).getTitle() ).isEqualTo( digitalProduct.getTitle() );
        assertThat( digitalProductResponseDTOList.get( 0 ).getId() ).isEqualTo( digitalProduct.getId() );
        assertThat( digitalProductResponseDTOList.get( 0 ).getDescription() ).isEqualTo( digitalProduct.getDescription() );

    }

    @Test
    @DisplayName( "deve lancar erro quando nao houver imagens pela categoria" )
    public void deveLancarErroQuandoNaoHouverItensPelaCategoria() throws EntityNotFoundException {

        when( categoryService.getById( category.getId() ) ).thenReturn(
                CategoryDTO.builder()
                        .id( category.getId() )
                        .description( category.getDescription() )
                        .build() );

        Throwable t = assertThrows( EntityNotFoundException.class, () -> digitalProductService.getAllProductsByCategory( 1L ) );

        assertTrue( t.getMessage().contains( "Não localizamos produtos." ) );

    }

    @Test
    @DisplayName( "deve listar produtos conforme consulta por titulo ou descricao" )
    public void deveListarProdutosViaConsulta() {

        List<DigitalProduct> digitalProductList = new ArrayList<>();

        DigitalProduct digitalProduct = createValidDigitalProductWithData( 1L, image, category, "Outra" );

        digitalProductList.add( digitalProduct );

        when( digitalProductRepository.findDigitalProductsByTitleOrDescription( digitalProduct.getDescription() ) )
                .thenReturn( digitalProductList );

        List< DigitalProductResponseDTO > digitalProductResponseDTOList = digitalProductService
                .getAllProductsBySearch( digitalProduct.getDescription() );

        assertThat( digitalProductResponseDTOList ).isNotNull();
        assertThat( digitalProductResponseDTOList.get( 0 ).getValue() ).isEqualTo( digitalProduct.getValue() );
        assertThat( digitalProductResponseDTOList.get( 0 ).getTitle() ).isEqualTo( digitalProduct.getTitle() );
        assertThat( digitalProductResponseDTOList.get( 0 ).getId() ).isEqualTo( digitalProduct.getId() );
        assertThat( digitalProductResponseDTOList.get( 0 ).getDescription() ).isEqualTo( digitalProduct.getDescription() );

    }

    @Test
    @DisplayName( "deve lancar erro quando nao houver resultado busca por titulo ou desc" )
    public void deveLancarErroSemResultadosBuscaPorTitOuDesc() throws EntityNotFoundException {

        Throwable t = assertThrows( EntityNotFoundException.class,
                () -> digitalProductService.getAllProductsBySearch( "Nada" ) );

        assertTrue( t.getMessage().contains( "Não localizamos produtos." ) );

    }

    @Test
    @DisplayName( "deve consultar produto pelo id" )
    public void deveConsultarPorId() {

        DigitalProduct digitalProduct = createValidDigitalProductWithData( 1L, image, category );

        when( digitalProductRepository.findById( 1L ) )
                .thenReturn( Optional.ofNullable( digitalProduct ) );

        DigitalProductResponseDTO digitalProductResponseDTO = digitalProductService
                .getById( digitalProduct.getId() );

        assertThat( digitalProductResponseDTO.getDescription() ).isEqualTo( digitalProduct.getDescription() );
        assertThat( digitalProductResponseDTO.getId() ).isEqualTo( digitalProduct.getId() );
        assertThat( digitalProductResponseDTO.getValue() ).isEqualTo( digitalProduct.getValue() );
        assertThat( digitalProductResponseDTO.getTitle() ).isEqualTo( digitalProduct.getTitle() );

    }

    @Test
    @DisplayName( "deve lancar erro quando nao encontrar produto por id" )
    public void deveLancarErroQuandoNaoEncontrarProdutoPorId() throws EntityNotFoundException {

        Long idInvalido = 1L;

        Throwable t = assertThrows( EntityNotFoundException.class,
                () -> digitalProductService.getById( idInvalido ) );

        assertTrue( t.getMessage().contains(
                String.format( "Produto com identificador [%d] não encontrado.", idInvalido ) ) );

    }

    @Test
    @DisplayName( "deve listar todos os produtos sem filtro" )
    public void deveListarTodosProdutosSemFiltro() {

        List<DigitalProduct> digitalProductList = new ArrayList<>();

        DigitalProduct digitalProduct = createValidDigitalProductWithData( 1L, image, category, "Outra" );
        DigitalProduct digitalProduct1 = createValidDigitalProductWithData( 1L, image, category );

        digitalProductList.add( digitalProduct );
        digitalProductList.add( digitalProduct1 );

        when( digitalProductRepository.findAll() )
                .thenReturn( digitalProductList );

        List< DigitalProductResponseDTO > digitalProductResponseDTOList = digitalProductService
                .getAll();

        assertThat( digitalProductResponseDTOList ).isNotNull();
        assertThat( digitalProductResponseDTOList.get( 0 ).getValue() ).isEqualTo( digitalProduct.getValue() );
        assertThat( digitalProductResponseDTOList.get( 0 ).getTitle() ).isEqualTo( digitalProduct.getTitle() );
        assertThat( digitalProductResponseDTOList.get( 0 ).getId() ).isEqualTo( digitalProduct.getId() );
        assertThat( digitalProductResponseDTOList.get( 0 ).getDescription() ).isEqualTo( digitalProduct.getDescription() );

    }

    @Test
    @DisplayName( "deve lancar erro quando nao houver produtos consulta sem filtros" )
    public void deveLancarErroConsultaSemFiltrosSemProdutos() throws EntityNotFoundException {

        Throwable t = assertThrows( EntityNotFoundException.class,
                () -> digitalProductService.getAll() );

        t.getMessage().contains( "Não localizamos produtos." );

    }

    private DigitalProduct createValidDigitalProductWithData( Long id, Image image, Category category ) {
        return DigitalProduct.builder()
                .id( id )
                .image( image )
                .title( "Imagem de Teste" )
                .description( "Teste" )
                .value( 120D )
                .category( category )
                .build();
    }

    private DigitalProduct createValidDigitalProductWithData( Long id, Image image, Category category, String desc ) {
        return DigitalProduct.builder()
                .id( id )
                .image( image )
                .title( "Imagem de Teste" )
                .description( desc )
                .value( 120D )
                .category( category )
                .build();
    }

}