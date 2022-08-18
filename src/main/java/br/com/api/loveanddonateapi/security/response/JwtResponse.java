package br.com.api.loveanddonateapi.security.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private String email;
    private String username;

    private Long id;

    private List roles;

    public JwtResponse( String jwt, Long id, String email, String username, List roles ) {
        this.id = id;
        this.accessToken = jwt;
        this.email = email;
        this.username = username;
        this.roles = roles;
    }

}