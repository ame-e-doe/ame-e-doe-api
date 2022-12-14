package br.com.loveanddonateapi.controller;

import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RestController
@RequestMapping( "/api/test" )
@Api(tags = {"Rotas"})
public class TestController {

    @GetMapping( "/all" )
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping( "/user" )
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping( "/mod" )
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping( "/admin" )
    @PreAuthorize( "hasRole('ROLE_ADMIN')" )
    public String adminAccess() {
        return "Admin Board.";
    }
}