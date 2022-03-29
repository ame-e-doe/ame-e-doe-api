package com.api.loveanddonateapi.web;

import com.api.loveanddonateapi.domain.Registration;
import com.api.loveanddonateapi.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( path = "api/v1/registration" )
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register( @RequestBody Registration registration ) {
        return registrationService.register( registration );
    }

    @GetMapping(path = "confirm")
    public String confirm( @RequestParam( "token" ) String token ) {
        return registrationService.confirmToken( token );
    }

}
