package com.api.loveanddonateapi.controller;

import com.api.loveanddonateapi.dto.response.MessageResponse;
import com.api.loveanddonateapi.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/confirm" )
public class ConfirmTokenController {

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @PutMapping
    public ResponseEntity< MessageResponse > confirmToken() {
        confirmationTokenService.
    }

}
