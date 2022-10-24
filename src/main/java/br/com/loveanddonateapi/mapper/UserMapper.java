package br.com.loveanddonateapi.mapper;

import br.com.loveanddonateapi.dto.user.UserDTO;
import br.com.loveanddonateapi.models.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class UserMapper {

    public static User userDtoToEntity( UserDTO userDTO ) {
        return User.builder()
                .firstName( userDTO.getFirstName() )
                .lastName( userDTO.getLastName() )
                .password( userDTO.getPassword() )
                .username( userDTO.getEmail() )
                .build();
    }

    @Bean
    public static PasswordEncoder encrypt() {
        return new BCryptPasswordEncoder( 11 );
    }

}
