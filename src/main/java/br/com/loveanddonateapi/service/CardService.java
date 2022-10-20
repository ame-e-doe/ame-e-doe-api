package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.dto.CardDTO;
import br.com.loveanddonateapi.exception.EntityExistValidateException;
import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.models.Card;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.repository.CardRepository;
import br.com.loveanddonateapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CardService{

    UserRepository userRepository;

    CardRepository cardRepository;

    UserService userService;

//    TODO: Criar regra para validar data de expiracao
//    TODO: Criar regra para validar card number
    public CardDTO save( Card card, String email ) {
        Optional< User > user = userRepository.findByEmail( email );
        cardExist( card.getCardNumber(), user.get().getId() );
        card.setUser( user.get() );
        return new CardDTO( cardRepository.save( card ) );
    }

    public CardDTO getById( Long id ) {
        return cardRepository.findById( id ).map( card -> new CardDTO( card ) )
                .orElseThrow( () -> new EntityNotFoundException( String.format( "Cartão com identificador [%d] não encontrado.", id ) ) );
    }

    public List< CardDTO > getAll( String email ) {
        Optional< User > user = userRepository.findByEmail( email );
        return cardRepository.findCardsByUserId( user.get().getId() ).stream()
                .map( card -> new CardDTO( card ) ).collect( Collectors.toList() );
    }

    public void delete( Long id ) {
        Card card = cardRepository.findById( id )
                .orElseThrow( () -> new EntityNotFoundException( String.format( "Cartão com identificador [%d] não encontrado.", id ) ) );
        cardRepository.delete( card );
    }

    private void cardExist( String cardNumber, Long idUser ) {
        if( cardRepository.findCardByCardNumberAndUser( cardNumber, idUser ).isPresent() ) {
            throw new EntityExistValidateException( "Cartão já cadastrado." );
        }
    }
}
