package br.com.loveanddonateapi.unit.controller;

import br.com.loveanddonateapi.dto.user.UserDTO;
import br.com.loveanddonateapi.unit.utils.MapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith( MockitoExtension.class )
@ActiveProfiles( "test" )
public class UserControllerTest extends MapperUtils {

    private static final String PATH = "/api";
    private static final String BASE_URL = "/user";

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName( "Deve criar usuário com sucesso" )
    public void deveCriarUsuarioComSucesso() throws Exception {

//        UserDTO userDTO = UserDTO.builder()
//                .firstName( "Teste" )
//                .lastName( "Teste" )
//                .email( "teste@teste.com" )
//                .build();
//
//        mockMvc.perform( post( PATH + BASE_URL )
//                        .contentType( MediaType.APPLICATION_JSON )
//                        .content( objectToJson( userDTO ) ) )
//                .andExpect( status().isCreated() );
//                .andExpect( jsonPath( "message" ).isNotEmpty() );
    }

    @Test
    @DisplayName( "deve lançar erro de validação ao tentar salvar um user com dados insuficientes" )
    public void deveLancarErroAoCadastrarUsuarioInvalido() throws Exception {

        String json = new ObjectMapper().writeValueAsString( new UserDTO() );

        mockMvc.perform( post( PATH + BASE_URL )
                .contentType( MediaType.APPLICATION_JSON )
                .content( json ) )
                .andExpect( status()
                        .isBadRequest() );
    }

}