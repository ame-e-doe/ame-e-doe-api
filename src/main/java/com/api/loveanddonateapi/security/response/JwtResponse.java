package com.api.loveanddonateapi.security.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;

    private Long id;

    private List roles;

    public JwtResponse(String jwt, Long id, String username, List roles) {
        this.id = id;
        this.token = jwt;
        this.username = username;
        this.roles = roles;
    }

}
