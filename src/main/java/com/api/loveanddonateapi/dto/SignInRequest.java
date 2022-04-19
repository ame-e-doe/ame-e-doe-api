package com.api.loveanddonateapi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class SignInRequest {
    private final String email;
    private final String password;
}
