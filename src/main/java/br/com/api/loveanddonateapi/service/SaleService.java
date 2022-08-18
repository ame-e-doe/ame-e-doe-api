package br.com.api.loveanddonateapi.service;

import br.com.api.loveanddonateapi.repository.SaleRepository;
import br.com.api.loveanddonateapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private UserRepository userRepository;


   /* public SaleDto createSale( SaleDto saleDto, Long userId ) {
        User user = userRepository.findById( userId )
                .orElseThrow(() -> new EntityNotFoundException( "Usuario de id: " + userId + " não encontrado." ) );
        Sale sale = mapper.map( saleDto, Sale.class );
        sale.setUser( user );
        return mapper.map( saleRepository.save( sale ), SaleDto.class );
    }

    public List<SaleDto> getAllSales( Long userId ) {
        return saleRepository.findAllByUserId(userId)
                .stream()
                .map( sale -> mapper.map( sale, SaleDto.class ) )
                .collect( Collectors.toList() );
    }

    public SaleDto getSaleById( Long saleId ) {
        return saleRepository.findById( saleId )
                .map( sale -> mapper.map( sale, SaleDto.class ) )
                .orElseThrow(() -> new EntityNotFoundException( "Pedido com id: " + saleId + " não encontrado." ));
    }*/
}
