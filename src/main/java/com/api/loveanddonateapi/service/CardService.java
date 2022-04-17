package com.api.loveanddonateapi.service;

import com.api.loveanddonateapi.domain.Card;
import com.api.loveanddonateapi.domain.User;
import com.api.loveanddonateapi.dto.CardDto;
import com.api.loveanddonateapi.exception.EntityNotFoundException;
import com.api.loveanddonateapi.repository.CardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final UserService userService;
    private final ModelMapper mapper = new ModelMapper();

    public CardService( CardRepository cardRepository, UserService userService ) {
        this.cardRepository = cardRepository;
        this.userService = userService;
    }

    public CardDto createCard( CardDto cardDto, Long userId ) {
        Card card = mapper.map( cardDto, Card.class );
        User user = this.userService.getUserById( userId )
                .orElseThrow( () -> new EntityNotFoundException( "Usuario de id: " + userId + " não encontrado." ) );
        card.setUser( user );
        return mapper.map( cardRepository.save( card ), CardDto.class );
    }

    public List< CardDto > getAllCards( Long userId ) {
        return cardRepository.findAllCardsByUserId( userId )
                .stream()
                .map( card -> mapper.map( card, CardDto.class ) )
                .collect( Collectors.toList() );
    }

    public void deleteCardById( Long cardId ) {
        cardRepository.delete( cardRepository.findById( cardId )
                .orElseThrow( () -> new EntityNotFoundException( "Cartão de id: " + cardId + " não encontrado." ) ) );
    }
}
