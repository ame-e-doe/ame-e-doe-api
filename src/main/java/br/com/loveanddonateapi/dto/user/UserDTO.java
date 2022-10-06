package br.com.loveanddonateapi.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel( value = "create user", description = "Data Transfer for create user" )
public class UserDTO {

    @ApiModelProperty( required = true )
    @NotBlank( message = "{required.field}" )
    private String firstName;

    @ApiModelProperty( required = true )
    @NotBlank( message = "{required.field}" )
    private String lastName;

    @ApiModelProperty( required = true )
    @Email
    @NotBlank( message = "{required.field}" )
    private String email;

    @ApiModelProperty( required = true, notes = "Min 8, Max 16" )
    @Length( min = 8, max = 16, message = "{password.validation}")
    @NotBlank( message = "{required.field}")
    private String password;

    private String role;

}