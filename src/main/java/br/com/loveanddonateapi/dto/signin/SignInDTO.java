package br.com.loveanddonateapi.dto.signin;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@ToString
@Getter
@JsonInclude( JsonInclude.Include.NON_NULL )
public class SignInDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private final String password;

}
