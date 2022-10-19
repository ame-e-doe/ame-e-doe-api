package br.com.loveanddonateapi.dto;

import br.com.loveanddonateapi.models.Card;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Null
    private Long id;

    @NotBlank(message = "{required.field}")
    private String cardNumber;

    @NotBlank(message = "{required.field}")
    private String securityCode;

    @NotBlank(message = "{required.field}")
    private String printedName;

    @NotBlank(message = "{required.field}")
    private String expirationDate;

    public CardDTO( Card card ) {
        this.setId( card.getId() );
        this.setCardNumber( card.getCardNumber() );
        this.setSecurityCode( card.getSecurityCode() );
        this.setPrintedName( card.getPrintedName() );
        this.setExpirationDate( card.getExpirationDate() );
    }

    public static Card asEntity(CardDTO dto) {
        Card card = new Card();
        card.setCardNumber(dto.getCardNumber());
        card.setSecurityCode(dto.getSecurityCode());
        card.setPrintedName(dto.getPrintedName());
        card.setExpirationDate(dto.getExpirationDate());
        return card;
    }
}
