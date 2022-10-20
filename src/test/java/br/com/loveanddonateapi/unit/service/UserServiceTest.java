package br.com.loveanddonateapi.unit.service;

import br.com.loveanddonateapi.models.Role;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.models.enums.ERole;
import br.com.loveanddonateapi.repository.RoleRepository;
import br.com.loveanddonateapi.repository.UserRepository;
import br.com.loveanddonateapi.service.CartService;
import br.com.loveanddonateapi.service.UserService;
import br.com.loveanddonateapi.utils.EmailUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles( "test" )
public class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    CartService cartService;

    @Test
    @DisplayName( "deve cadastrar um usuário com sucesso" )
    public void saveSuccessUserTest() {

        User user = createValidUser();

        when( roleRepository.findByName( ERole.ROLE_USER.name() ) ).thenReturn( Optional.ofNullable( gerenateRole() ) );

        when( userRepository.save( user ) ).thenReturn( User.builder()
                        .id( 1L )
                        .firstName( "Teste" )
                        .lastName( "do Teste" )
                        .username( "teste@teste.com" )
                        .password( "Teste@123" )
                        .enabled( false )
                        .locked( true )
                        .roles( null )
                        .build() );

        String email = userService.registerUser( user );

        assertThat( email ).isEqualTo( EmailUtils.formatterEmail( user.getUsername() ) );

    }

    private Role gerenateRole() {
        return Role.builder()
                .id( 1L )
                .name( "ROLE_USER" )
                .build();
    }

    private static User createValidUser() {
        return User.builder()
                .firstName( "Teste" )
                .lastName( "do Teste" )
                .username( "teste@teste.com" )
                .password( "Teste@123" )
                .enabled( false )
                .locked( true )
                .roles( null )
                .build();

    }

}