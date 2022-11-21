package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.dto.SaleCreateDTO;
import br.com.loveanddonateapi.dto.response.SaleResponseDTO;
import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.models.Cart;
import br.com.loveanddonateapi.models.Sale;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.repository.CartItemRepository;
import br.com.loveanddonateapi.repository.SaleRepository;
import br.com.loveanddonateapi.configuration.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService implements BaseService<SaleResponseDTO> {

//    TODO: Refactor do Service de SALE

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    CartService cartService;
    @Autowired
    CartItemRepository cartItemRepository;

    public SaleResponseDTO createSale(SaleCreateDTO dto, Long idUser) {
        Sale sale = dto.asEntity(dto);
        User user = userService.getById(idUser);
        Cart cart = cartService.getCartByUser(user.getId());

        cart.setTotalPrice(0.0);
        cart.setUser(user);
        
        cartItemRepository.deleteAllByIdCart(cart.getId());
        cartService.updateCart(cart);

        sale.setUser(user);

        return new SaleResponseDTO(saleRepository.save(sale));
    }

    @Override
    public SaleResponseDTO create( SaleResponseDTO saleResponseDto, String email ) {
        return null;
    }

    @Override
    public SaleResponseDTO getById(Long id) {
        return saleRepository.findById(id)
                .map(sale -> new SaleResponseDTO(sale))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Pedido com identificador [%d] não encontrado.", id)));
    }

    public List<SaleResponseDTO> getAll(Long idUser) {
        //User user = userService.getById( idUser );
        return saleRepository.findAllByUserId(idUser)
                .stream()
                .map(sale -> new SaleResponseDTO(sale))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        //essa classe não implementa este método.
    }

}
