package com.api.loveanddonateapi.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SignInDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String email;
    private final String password;

}
