package br.com.loveanddonateapi.configuration.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String accessToken;
    private String firstName;
    private String email;
    private Long id;

    public JwtResponse( String jwt, String firstName, String email, Long id ) {
        this.accessToken = jwt;
        this.firstName = firstName;
        this.email = email;
        this.id = id;
    }

}
