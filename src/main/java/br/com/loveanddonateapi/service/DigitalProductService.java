package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.dto.DigitalProductDTO;
import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.models.Category;
import br.com.loveanddonateapi.models.DigitalProduct;
import br.com.loveanddonateapi.repository.CategoryRepository;
import br.com.loveanddonateapi.repository.DigitalProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DigitalProductService implements BaseService< DigitalProductDTO, DigitalProduct> {

    @Autowired
    private DigitalProductRepository digitalProductRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<DigitalProductDTO> getAllProductsByCategory(Long idCategory) {
        Category category = categoryRepository.findById(idCategory)
                .orElseThrow(() -> new EntityNotFoundException("Categoria de id: " + idCategory + " não encontrado."));
        return this.digitalProductRepository
                .getDigitalProductsByCategory(category).stream().map(product -> new DigitalProductDTO(product)).collect(Collectors.toList());
    }

    public List<DigitalProductDTO> getAllProductsBySearch(String search) {
        return this.digitalProductRepository
                .findDigitalProductsByTitleOrDescription(search).stream().map(product -> new DigitalProductDTO(product)).collect(Collectors.toList());
    }

    @Override
    public DigitalProduct createOrUpdate(DigitalProductDTO digitalProductDTO) {
        try {
            throw new Exception("Esse método não vai ser implementado por agora.");
        } catch (Exception e) {
            throw new RuntimeException("Esse método não vai ser implementado por agora.");
        }
    }

    @Override
    public DigitalProductDTO getById(Long id) {
        return this.digitalProductRepository.findById(id)
                .map(product -> new DigitalProductDTO(product))
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Produto com identificador [%d] não encontrado.", id)));
    }

    @Override
    public List<DigitalProductDTO> getAll() {
        //precisa pegar o usuario logado
        return this.digitalProductRepository.findAll()
                .stream().map(product -> new DigitalProductDTO(product))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        DigitalProduct product = digitalProductRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Produto com identificador [%d] não encontrado.", id)));
        this.digitalProductRepository.delete(product);
    }

    /*public DigitalProductsByUserDTO getAllProductsByUser(Long idUser) {
        User user = this.userRepository.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("Usuario de id: " + idUser + " não encontrado."));
        return mapper.map(user, DigitalProductsByUserDTO.class);
    }*/
}
