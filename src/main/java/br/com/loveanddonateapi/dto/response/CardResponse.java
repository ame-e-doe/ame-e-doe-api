package br.com.loveanddonateapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardResponse {

    private Long userId;
    private String cardNumber;
    private String securityCode;
    private String printedName;
    private String ExpirationDate;

}
