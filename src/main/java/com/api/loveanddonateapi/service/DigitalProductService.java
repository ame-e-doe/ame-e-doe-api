package com.api.loveanddonateapi.service;

import com.api.loveanddonateapi.domain.Category;
import com.api.loveanddonateapi.domain.User;
import com.api.loveanddonateapi.dto.DigitalProductDto;
import com.api.loveanddonateapi.dto.DigitalProductsByUser;
import com.api.loveanddonateapi.exception.EntityNotFoundException;
import com.api.loveanddonateapi.repository.CategoryRepository;
import com.api.loveanddonateapi.repository.DigitalProductRepository;
import com.api.loveanddonateapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DigitalProductService {

    private final DigitalProductRepository dpRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private ModelMapper mapper = new ModelMapper();

    public DigitalProductService( DigitalProductRepository dpRepository, CategoryRepository categoryRepository, UserRepository userRepository ) {
        this.dpRepository = dpRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List< DigitalProductDto > getAllProducts() {
        return this.dpRepository.findAll()
                .stream()
                .map( products -> mapper.map( products, DigitalProductDto.class ) )
                .collect( Collectors.toList() );
    }

    public DigitalProductDto getProductById( Long idProduct ) {
        return this.dpRepository.findById( idProduct )
                .map( product -> mapper.map( product, DigitalProductDto.class ) )
                .orElseThrow( () -> new EntityNotFoundException( "Produto de id: " + idProduct + " não encontrado." ) );
    }

    public List< DigitalProductDto > getAllProductsByCategory( Long idCategory ) {
        Category category = this.categoryRepository.findById( idCategory )
                .orElseThrow( () -> new EntityNotFoundException( "Categoria de id: " + idCategory + " não encontrado." ) );
        return this.dpRepository.getDigitalProductsByCategory( category )
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
        return this.dpRepository
                .findDigitalProductsByTitleOrDescription( search )
                .stream()
                .map( product -> mapper.map( product, DigitalProductDto.class ) )
                .collect( Collectors.toList() );
    }

}
