package br.com.loveanddonateapi.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel( value = "update user", description = "Data Transfer for update user" )
public class UpdateUserDTO {

    @ApiModelProperty( required = true )
    @NotBlank( message = "{required.field}" )
    private String firstName;

    @ApiModelProperty( required = true )
    @NotBlank( message = "{required.field}" )
    private String lastName;

}