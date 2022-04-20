package com.api.loveanddonateapi.service;

import com.api.loveanddonateapi.dto.DigitalProductDto;
import com.api.loveanddonateapi.dto.DigitalProductsByUser;
import com.api.loveanddonateapi.exception.EntityNotFoundException;
import com.api.loveanddonateapi.models.Category;
import com.api.loveanddonateapi.models.User;
import com.api.loveanddonateapi.repository.CategoryRepository;
import com.api.loveanddonateapi.repository.DigitalProductRepository;
import com.api.loveanddonateapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DigitalProductService {

    @Autowired
    private DigitalProductRepository digitalProductRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    private ModelMapper mapper = new ModelMapper();

    public List< DigitalProductDto > getAllProducts() {
        return this.digitalProductRepository.findAll()
                .stream()
                .map( products -> mapper.map( products, DigitalProductDto.class ) )
                .collect( Collectors.toList() );
    }

    public DigitalProductDto getProductById( Long idProduct ) {
        return this.digitalProductRepository.findById( idProduct )
                .map( product -> mapper.map( product, DigitalProductDto.class ) )
                .orElseThrow( () -> new EntityNotFoundException( "Produto de id: " + idProduct + " não encontrado." ) );
    }

    public List< DigitalProductDto > getAllProductsByCategory( Long idCategory ) {
        Category category = categoryRepository.findById( idCategory )
                .orElseThrow( () -> new EntityNotFoundException( "Categoria de id: " + idCategory + " não encontrado." ) );
        return this.digitalProductRepository.getDigitalProductsByCategory( category )
                .stream()
                .map( product -> mapper.map( product, DigitalProductDto.class ) )
                .collect( Collectors.toList() );
    }

    public DigitalProductsByUser getAllProductsByUser( Long idUser ) {
        User user = this.userRepository.findById( idUser )
                .orElseThrow( () -> new EntityNotFoundException( "Usuario de id: " + idUser + " não encontrado." ) );
        return mapper.map( user, DigitalProductsByUser.class );
    }

    public List< DigitalProductDto > getAllProductsBySearch( String search ) {
        return this.digitalProductRepository
                .findDigitalProductsByTitleOrDescription( search )
                .stream()
                .map( product -> mapper.map( product, DigitalProductDto.class ) )
                .collect( Collectors.toList() );
    }

}
