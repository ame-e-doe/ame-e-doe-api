package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.dto.response.DigitalProductResponseDTO;
import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.mapper.CategoryMapper;
import br.com.loveanddonateapi.mapper.DigitalProductMapper;
import br.com.loveanddonateapi.models.Category;
import br.com.loveanddonateapi.models.DigitalProduct;
import br.com.loveanddonateapi.repository.DigitalProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DigitalProductService {

    @Autowired
    private DigitalProductRepository digitalProductRepository;

    @Autowired
    private CategoryService categoryService;

    public List< DigitalProductResponseDTO > getAllProductsByCategory( Long idCategory ) {

        List< DigitalProduct > digitalProductList = findByCategory( idCategory );

        if( Objects.isNull( digitalProductList ) || digitalProductList.isEmpty() ) {
            throw new EntityNotFoundException( "Não localizamos produtos." );
        }

        return digitalProductList
                .stream()
                .map( product -> DigitalProductMapper.digitalProductEntityToDTO( product )  )
                .collect( Collectors.toList() ) ;

    }

    private List< DigitalProduct > findByCategory( Long id ) {

        Category category = CategoryMapper
                .categoryDTOToEntity( categoryService.getById( id ) );

        List<DigitalProduct> digitalProductList =
                digitalProductRepository
                        .getDigitalProductsByCategory( category );
        return digitalProductList;
    }

    public List< DigitalProductResponseDTO > getAllProductsBySearch( String search ) {

        List< DigitalProduct > digitalProducts = digitalProductRepository
                .findDigitalProductsByTitleOrDescription( search );

        if( Objects.isNull( digitalProducts ) || digitalProducts.isEmpty() ) {
            throw new EntityNotFoundException( "Não localizamos produtos." );
        }

        return digitalProducts
                .stream()
                .map( product -> DigitalProductMapper.digitalProductEntityToDTO( product ) )
                .collect( Collectors.toList() );
    }

    public DigitalProductResponseDTO getById( Long id ) {

        return digitalProductRepository.findById( id )
                .map( product -> DigitalProductMapper.digitalProductEntityToDTO( product )  )
                .orElseThrow( () -> new EntityNotFoundException(
                        String.format( "Produto com identificador [%d] não encontrado.", id ) ) );
    }

    public List< DigitalProductResponseDTO > getAll() {

        List< DigitalProduct > digitalProducts = digitalProductRepository.findAll();

        if( Objects.isNull( digitalProducts ) || digitalProducts.isEmpty() ) {
            throw new EntityNotFoundException( "Não localizamos produtos." );
        }

        return digitalProducts
                .stream()
                .map( product -> DigitalProductMapper.digitalProductEntityToDTO( product )  )
                .collect( Collectors.toList() );
    }

}