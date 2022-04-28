package com.api.loveanddonateapi.dto.signup;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@JsonInclude( JsonInclude.Include.NON_NULL )
@AllArgsConstructor
@Getter
public class SignUpDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Email( message = "{email.invalid}")
    @NotBlank( message = "{required.field}")
    private String email;

    @NotBlank( message = "{required.field}")
    @Length( min = 8, max = 20, message = "{invalid.field}")
    private String password;

    private String role;
}
