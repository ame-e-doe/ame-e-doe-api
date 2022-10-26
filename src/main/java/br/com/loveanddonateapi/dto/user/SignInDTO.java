package br.com.loveanddonateapi.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Data
@Builder
@JsonInclude( JsonInclude.Include.NON_NULL )
public class SignInDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private final String password;

}
