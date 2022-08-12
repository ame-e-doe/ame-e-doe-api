package com.api.loveanddonateapi.dto;

import com.api.loveanddonateapi.models.Card;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@JsonInclude( JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank( message = "{required.field}")
    private String cardNumber;

    @NotBlank( message = "{required.field}")
    private String securityCode;

    @NotBlank( message = "{required.field}")
    private String printedName;

    @NotBlank( message = "{required.field}")
    private String expirationDate;

    public CardDTO(Card entity) {
        this.setCardNumber(entity.getCardNumber());
        this.setSecurityCode(entity.getSecurityCode());
        this.setPrintedName(entity.getPrintedName());
        this.setExpirationDate(entity.getExpirationDate());
    }

    public Card asEntity(CardDTO dto) {
        Card card = new Card();
        card.setCardNumber(dto.getCardNumber());
        card.setSecurityCode(dto.getSecurityCode());
        card.setPrintedName(dto.getPrintedName());
        card.setExpirationDate(dto.getExpirationDate());
        return card;
    }
}
