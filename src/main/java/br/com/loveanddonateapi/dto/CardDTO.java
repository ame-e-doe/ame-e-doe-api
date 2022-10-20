package br.com.loveanddonateapi.dto;

import br.com.loveanddonateapi.models.Card;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CardDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "{required.field}")
    private String cardNumber;

    @NotBlank(message = "{required.field}")
    private String securityCode;

    @NotBlank(message = "{required.field}")
    private String printedName;

    @NotBlank(message = "{required.field}")
    private String expirationDate;

    public CardDTO( Card card ) {
        this.setCardNumber( card.getCardNumber() );
        this.setSecurityCode( card.getSecurityCode() );
        this.setPrintedName( card.getPrintedName() );
        this.setExpirationDate( card.getExpirationDate() );
    }

    public static Card asEntity( CardDTO dto ) {
        Card card = new Card();
        card.setCardNumber( dto.getCardNumber() );
        card.setSecurityCode( dto.getSecurityCode() );
        card.setPrintedName( dto.getPrintedName() );
        card.setExpirationDate( dto.getExpirationDate() );
        return card;
    }
}
