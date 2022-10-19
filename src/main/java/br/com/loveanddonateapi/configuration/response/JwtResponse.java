package br.com.loveanddonateapi.configuration.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String accessToken;
    private String email;

    public JwtResponse( String jwt, String email ) {
        this.accessToken = jwt;
        this.email = email;
    }

}
