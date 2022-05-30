package com.api.loveanddonateapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@JsonInclude( JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
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

}
