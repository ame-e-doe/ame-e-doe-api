package com.api.loveanddonateapi.dto.signup;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude( JsonInclude.Include.NON_NULL )
public class SignUpDTOResponse {

    private String email;

}
