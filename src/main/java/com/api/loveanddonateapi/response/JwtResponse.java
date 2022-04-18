package com.api.loveanddonateapi.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private List roles;

    public JwtResponse(String jwt, String username, List roles) {
        this.token = jwt;
        this.username = username;
        this.roles = roles;
    }

}
