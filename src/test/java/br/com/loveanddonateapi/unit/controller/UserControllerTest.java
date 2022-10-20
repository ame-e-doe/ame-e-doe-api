package br.com.loveanddonateapi.unit.controller;

import br.com.loveanddonateapi.dto.user.UserDTO;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.service.UserService;
import br.com.loveanddonateapi.unit.utils.MapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith( SpringExtension.class )
@ActiveProfiles( "test" )
public class UserControllerTest extends MapperUtils {

    private static final String PATH = "/api";
    private static final String BASE_URL = "/user";

    private static final String PATH_REGISTER = "/register";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    @DisplayName( "Deve criar usuário com sucesso" )
    public void deveCriarUsuarioComSucesso() throws Exception {

        UserDTO userDTO = createValidUser();

        BDDMockito.given( userService.registerUser( any( User.class ) ) )
                .willReturn( userDTO.getEmail() );

        mockMvc.perform( post( PATH + BASE_URL + PATH_REGISTER )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectToJson( userDTO ) ) )
                .andExpect( status().isCreated() )
                .andExpect( jsonPath( "message.email" ).value( userDTO.getEmail() ) );

    }

    @Test
    @DisplayName( "deve lançar erro de validação ao tentar salvar um user com dados insuficientes" )
    public void deveLancarErroAoCadastrarUsuarioInvalido() throws Exception {

        String json = new ObjectMapper().writeValueAsString( new UserDTO() );

        mockMvc.perform( post( PATH + BASE_URL + PATH_REGISTER )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( json ) )
                .andExpect( status().isBadRequest() )
                .andExpect( jsonPath( "errors" ).value( hasSize( 4 ) ) )
                .andExpect( jsonPath( "errors.[0].message", equalTo( "Campo Obrigatório" ) ) );

    }

    @Test
    @DisplayName( "deve lançar erro ao tentar cadastrar usuário com senha inválida" )
    public void deveLancarErroAoCadastrarUsuarioSenhaInvalida() throws Exception {

        UserDTO userDTO = createValidUser();

        userDTO.setPassword( "1" );

        String json = new ObjectMapper().writeValueAsString( userDTO );

        mockMvc.perform( post( PATH + BASE_URL  + PATH_REGISTER )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( json ) )
                .andExpect( status().isBadRequest() )
                .andExpect( jsonPath( "errors" ).value( hasSize( 1 ) ) )
                .andExpect( jsonPath( "errors.[0].message", equalTo( "A Senha deve conter no mínimo 8 e no máximo 16 carateres" ) ) );

    }

    @Test
    @DisplayName( "deve lançar erro ao tentar cadastrar usuário com email inválido" )
    public void deveLancarErroAoCadastrarUsuarioEmailInvalido() throws Exception {

        UserDTO userDTO = createValidUser();

        userDTO.setEmail( "1" );

        String json = new ObjectMapper().writeValueAsString( userDTO );

        mockMvc.perform( post( PATH + BASE_URL + PATH_REGISTER )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( json ) )
                .andExpect( status().isBadRequest() )
                .andExpect( jsonPath( "errors" ).value( hasSize( 1 ) ) )
                .andExpect( jsonPath( "errors.[0].message", equalTo( "O e-mail informado não é válido" ) ) );
    }

    private static UserDTO createValidUser() {
        UserDTO userDTO = UserDTO.builder()
                .firstName( "Teste" )
                .lastName( "Teste" )
                .password( "Teste@123" )
                .email( "teste@teste.com" )
                .build();
        return userDTO;
    }

}