package br.com.loveanddonateapi.dto;

import br.com.loveanddonateapi.mapper.CardMapper;
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
        CardMapper.cardEntityToCardDTO( card );
    }

}
