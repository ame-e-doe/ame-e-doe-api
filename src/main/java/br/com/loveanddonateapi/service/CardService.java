package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.dto.CardDTO;
import br.com.loveanddonateapi.exception.EntityExistValidateException;
import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.mapper.CardMapper;
import br.com.loveanddonateapi.models.Card;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class CardService {

    private final CardRepository cardRepository;

    private final UserService userService;

//    TODO: Criar regra para validar data de expiracao
//    TODO: Criar regra para validar card number
    public CardDTO save( Card card, Long idUser ) {
        User user = userService.getById( idUser );

        if( Objects.isNull( user ) ) {
            log.error( "user not found by id {}", idUser );
            throw new UsernameNotFoundException( "Usuário não encontrado para o id: " + idUser );
        }
        verifyCardExists( card.getCardNumber(), idUser );
        card.setUser( user );
        log.info( "save card {}, for id {}", card.getCardNumber(), idUser );
        return CardMapper
                .cardEntityToCardDTO(
                        cardRepository.save( card ) );
    }

    public CardDTO getByIdCard( Long idCard ) {
        log.info( "return card by id card {}", idCard );
        return cardRepository.findById( idCard ).map( card -> new CardDTO( card ) )
                .orElseThrow( () ->
                        new EntityNotFoundException(
                                String.format( "Cartão com identificador [%d] não encontrado.", idCard ) ) );
    }

    public List< CardDTO > getAll( Long idUser ) {

        log.info( "search cards for user id {}", idUser );
        List< CardDTO > cardDTOList = cardRepository
                .findCardsByUserId( idUser )
                .stream()
                .map( card -> new CardDTO( card ) )
                .collect( Collectors.toList() );

        if( Objects.isNull( cardDTOList ) || cardDTOList.isEmpty() ) {
            log.error( "no cards found by user id {}", idUser );
            throw new EntityNotFoundException( "Nenhum cartão encontrado para o usuário" );
        }
        log.info( "cards found: " );
        return cardDTOList;
    }

    public void delete( Long idCard ) {
        log.info( "delete card by id card {}", idCard );
        Card card = cardRepository.findById( idCard )
                .orElseThrow( () -> new EntityNotFoundException( String.format( "Cartão com identificador [%d] não encontrado.", idCard ) ) );
        cardRepository.delete( card );
    }

    private void verifyCardExists( String cardNumber, Long idUser ) {
        log.info( "localize card by card number {} and id user {}", cardNumber, idUser );
        if( cardRepository.findCardByCardNumberAndUser( cardNumber, idUser ).isPresent() ) {
            log.error( "card exists {}", cardNumber );
            throw new EntityExistValidateException( "Cartão já cadastrado." );
        }
    }
}
