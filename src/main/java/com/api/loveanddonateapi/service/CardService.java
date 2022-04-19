package com.api.loveanddonateapi.service;

import com.api.loveanddonateapi.models.Card;
import com.api.loveanddonateapi.models.User;
import com.api.loveanddonateapi.dto.CardDTO;
import com.api.loveanddonateapi.exception.EntityNotFoundException;
import com.api.loveanddonateapi.repository.CardRepository;
import com.api.loveanddonateapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    UserRepository userRepository;

    private final ModelMapper mapper = new ModelMapper();

    public CardDTO createCard( CardDTO cardDto, Long userId ) {
        Card card = mapper.map( cardDto, Card.class );

        Optional< User > user = userRepository.findById( userId );

        if( user.isPresent() ) {
            card.setUser( user.get() );
            return mapper.map( cardRepository.save( card ), CardDTO.class );
        }
        return null;
    }

    public List< CardDTO > getAllCards( Long userId ) {
        return cardRepository.findAllCardsByUserId( userId )
                .stream()
                .map( card -> mapper.map( card, CardDTO.class ) )
                .collect( Collectors.toList() );
    }

    public void deleteCardById( Long cardId ) {
        cardRepository.delete( cardRepository.findById( cardId )
                .orElseThrow( () -> new EntityNotFoundException( "Cartão de id: " + cardId + " não encontrado." ) ) );
    }
}
