package com.api.loveanddonateapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardDTO {

    private Long id;

    @NotEmpty(message = "O número do cartão é obrigatório.")
    @Length( max = 16, min = 13, message = "A quantidade minima de números é 13 e a máxima 16.")
    private String cardNumber;

    @NotNull(message = "O código de segurança não pode ser nulo.")
    @Positive
    private Integer securityCode;

    @NotEmpty(message = "O nome do cartão é obrigatório.")
    @Length( max = 55, min = 10, message = "A quantidade minima de caracteres é 10 e a máxima 55.")
    private String printedName;

    @NotNull(message = "A data de expiração é obrigatória")
    private LocalDate expirationDate;
}
