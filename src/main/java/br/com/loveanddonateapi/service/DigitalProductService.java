package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.dto.DigitalProductResponseDTO;
import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.models.Category;
import br.com.loveanddonateapi.repository.CategoryRepository;
import br.com.loveanddonateapi.repository.DigitalProductRepository;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DigitalProductService {

    @Autowired
    private DigitalProductRepository digitalProductRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<DigitalProductResponseDTO> getAllProductsByCategory(Long idCategory) {
        Category category = categoryRepository.findById(idCategory)
                .orElseThrow(() -> new EntityNotFoundException("Categoria de id: " + idCategory + " não encontrado."));
        return this.digitalProductRepository
                .getDigitalProductsByCategory(category).stream().map(product -> new DigitalProductResponseDTO(product)).collect(Collectors.toList());
    }

    public List<DigitalProductResponseDTO> getAllProductsBySearch(String search) {
        return this.digitalProductRepository
                .findDigitalProductsByTitleOrDescription(search).stream().map(product -> new DigitalProductResponseDTO(product)).collect(Collectors.toList());
    }

    public DigitalProductResponseDTO getById(Long id) {
        return this.digitalProductRepository.findById(id)
                .map(product -> new DigitalProductResponseDTO(product))
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Produto com identificador [%d] não encontrado.", id)));
    }

    public List<DigitalProductResponseDTO> getAll() {
        return this.digitalProductRepository.findAll()
                .stream().map(product -> new DigitalProductResponseDTO(product))
                .collect(Collectors.toList());
    }

}
