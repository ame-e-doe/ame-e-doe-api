package br.com.loveanddonateapi.dto.user;

import br.com.loveanddonateapi.models.email.Email;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude( JsonInclude.Include.NON_NULL )
@Setter
public class UserDTOResponse {

    private Email email;

}
