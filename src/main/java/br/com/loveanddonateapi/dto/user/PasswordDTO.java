package br.com.loveanddonateapi.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel( value = "update password user", description = "Data transfer for update password user")
public class PasswordDTO {

    @ApiModelProperty( required = true, notes = "Min 8, Max 16" )
    @Length( min = 8, max = 16, message = "{password.validation}")
    @NotBlank( message = "{required.field}")
    private String oldPassword;

    @ApiModelProperty( required = true, notes = "Min 8, Max 16" )
    @Length( min = 8, max = 16, message = "{password.validation}")
    @NotBlank( message = "{required.field}")
    private String newPassword;

}
