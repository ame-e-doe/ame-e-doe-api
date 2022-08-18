package br.com.loveanddonateapi.dto.signin.signup;

import br.com.loveanddonateapi.models.email.Email;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude( JsonInclude.Include.NON_NULL )
@Setter
public class SignUpDTOResponse {

    private Email email;

}
