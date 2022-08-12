package com.api.loveanddonateapi.dto.signin.signup;

import com.api.loveanddonateapi.models.email.Email;
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
