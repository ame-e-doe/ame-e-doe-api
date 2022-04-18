package com.api.loveanddonateapi.service;

import com.api.loveanddonateapi.domain.Card;
import com.api.loveanddonateapi.domain.User;
import com.api.loveanddonateapi.dto.CardDTO;
import com.api.loveanddonateapi.repository.CardRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserService userService;
    private final ModelMapper mapper = new ModelMapper();

    public CardDTO createCard( CardDTO cardDto, Long userId ) {
        Card card = mapper.map( cardDto, Card.class );

        Optional< User > user = this.userService.getUserById( userId );

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

    public void deleteCard( Long cardId ) {
        cardRepository.deleteById( cardId );
    }
}
