package br.com.loveanddonateapi.mapper;

import br.com.loveanddonateapi.dto.CardDTO;
import br.com.loveanddonateapi.models.Card;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class CardMapper {

    public static Card cardDtoToEntity( CardDTO cardDTO ) {
        return Card.builder()
                .cardNumber( cardDTO.getCardNumber() )
                .securityCode( cardDTO.getSecurityCode() )
                .printedName( cardDTO.getPrintedName() )
                .expirationDate( cardDTO.getExpirationDate() )
                .build();
    }

    public static CardDTO cardEntityToCardDTO( Card card ) {
        return CardDTO.builder()
                .id( card.getId() )
                .cardNumber( card.getCardNumber() )
                .securityCode( card.getSecurityCode() )
                .printedName( card.getPrintedName() )
                .expirationDate( card.getExpirationDate() )
                .build();
    }

}
