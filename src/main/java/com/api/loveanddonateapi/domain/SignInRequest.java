package com.api.loveanddonateapi.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class SignInRequest {
    private final String email;
    private final String password;
}
