package com.api.loveanddonateapi.service;

import com.api.loveanddonateapi.dto.response.CardResponse;
import com.api.loveanddonateapi.dto.response.MessageResponse;
import com.api.loveanddonateapi.models.Card;
import com.api.loveanddonateapi.models.User;
import com.api.loveanddonateapi.dto.CardDTO;
import com.api.loveanddonateapi.exception.EntityNotFoundException;
import com.api.loveanddonateapi.repository.CardRepository;
import com.api.loveanddonateapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    UserRepository userRepository;

    private ModelMapper mapper = new ModelMapper();

    public CardDTO createCard( CardDTO cardDTO, Long userId ) {
        Card card = mapper.map( cardDTO, Card.class );

        log.debug( "Given create card find user by id {}", userId );
        Optional< User > user = userRepository.findById( userId );

        log.debug( "If user exists save card for id user {}", userId );
        if( user.isPresent() ) {
            card.setUser( user.get() );
            return mapper.map( cardRepository.save( card ), CardDTO.class );
        }
        return null;
    }

    public ResponseEntity< ? > getCard( Long userId ) {
        log.debug( "Find all cards for user with userId {}", userId );

        Card card = cardRepository.findByUserId( userId );

        if( !Objects.isNull( card ) ) {
            return ResponseEntity
                    .ok( new CardResponse(
                            card.getUser().getId(),
                            card.getCardNumber(),
                            card.getSecurityCode(),
                            card.getPrintedName(),
                            card.getExpirationDate()
                    ) );
        } else
            return ResponseEntity
                    .badRequest()
                    .body(
                            new MessageResponse( "Não existe cartão cadastrado para este usuário" ) );
    }

    public void deleteCardById( Long cardId ) {
        log.debug( "Delete card by cardId {}", cardId );
        cardRepository.delete( cardRepository.findById( cardId )
                .orElseThrow( () -> new EntityNotFoundException( "Cartão de id: " + cardId + " não encontrado." ) ) );
    }
}
