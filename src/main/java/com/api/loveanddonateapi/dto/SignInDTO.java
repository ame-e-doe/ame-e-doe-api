package com.api.loveanddonateapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@AllArgsConstructor
@ToString
@Getter
@JsonInclude( JsonInclude.Include.NON_NULL )
public class SignInDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private final String password;

}
