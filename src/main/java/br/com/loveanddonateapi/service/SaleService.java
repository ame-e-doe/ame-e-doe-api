package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.dto.SaleCreateDTO;
import br.com.loveanddonateapi.dto.SaleResponseDTO;
import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.models.Sale;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.repository.SaleRepository;
import br.com.loveanddonateapi.configuration.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService implements BaseService<SaleResponseDTO> {
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;

    public SaleResponseDTO createSale(SaleCreateDTO dto, String token) {
        Long userId = Long.parseLong(jwtUtils.getUserFromJwtToken(token));
        Sale sale = dto.asEntity(dto);
        User user = userService.getById(userId);
        sale.setUser(user);
        return new SaleResponseDTO(saleRepository.save(sale));
    }

    @Override
    public SaleResponseDTO create(SaleResponseDTO saleResponseDto, String token) {
        return null;
    }

    @Override
    public SaleResponseDTO getById(Long id) {
        return saleRepository.findById(id)
                .map(sale -> new SaleResponseDTO(sale))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Pedido com identificador [%d] não encontrado.", id)));
    }

    @Override
    public List<SaleResponseDTO> getAll(String token) {
        Long userId = Long.parseLong(jwtUtils.getUserFromJwtToken(token));
        return saleRepository.findAllByUserId(userId)
                .stream()
                .map(sale -> new SaleResponseDTO(sale))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        //essa classe não implementa este método.
    }

}
