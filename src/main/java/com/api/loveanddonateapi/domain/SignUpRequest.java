package com.api.loveanddonateapi.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SignUpRequest {
    private final String email;
    private final String password;
    private final String role;
}
