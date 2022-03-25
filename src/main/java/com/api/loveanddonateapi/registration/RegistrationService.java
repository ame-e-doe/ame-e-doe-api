package com.api.loveanddonateapi.registration;

import com.api.loveanddonateapi.user.User;
import com.api.loveanddonateapi.user.UserRole;
import com.api.loveanddonateapi.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest registrationRequest) {
        boolean isValidEmail = emailValidator
                .test( registrationRequest.getEmail() );
        if( !isValidEmail ) {
            throw new IllegalStateException("email not valid");
        }

        return userService.signUpUser(
                new User(
                        registrationRequest.getEmail(),
                        registrationRequest.getPassword(),
                        UserRole.USER
                )
        );
    }
}
