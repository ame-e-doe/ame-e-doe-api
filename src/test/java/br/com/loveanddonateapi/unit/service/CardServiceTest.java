package br.com.loveanddonateapi.unit.service;

import br.com.loveanddonateapi.dto.CardDTO;
import br.com.loveanddonateapi.dto.response.MessageResponse;
import br.com.loveanddonateapi.exception.EntityExistValidateException;
import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.models.Card;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.repository.CardRepository;
import br.com.loveanddonateapi.service.CardService;
import br.com.loveanddonateapi.service.UserService;
import br.com.loveanddonateapi.unit.utils.UserUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles( "test" )
public class CardServiceTest {

    @MockBean
    CardRepository cardRepository;

    @MockBean
    UserService userService;

    @Autowired
    CardService cardService;

    @Test
    @DisplayName( "deve salvar um cartao com sucesso" )
    public void deveSalvarUmCartaoComSucesso() {
        User user = UserUtils.createValidUser();

        when( userService.getById( 1L ) ).thenReturn( user );

        Card card = generateValidCard();

        when( cardRepository.save( card ) ).thenReturn( Card.builder()
                .id( 1L )
                .cardNumber( card.getCardNumber() )
                .securityCode( card.getSecurityCode() )
                .printedName( card.getPrintedName() )
                .expirationDate( card.getExpirationDate() )
                .user( user )
                .build() );

        CardDTO cardDTO = cardService.save( card, user.getId() );

        assertThat( cardDTO ).isNotNull();
        assertThat( cardDTO.getCardNumber() ).isEqualTo( card.getCardNumber() );
        assertThat( cardDTO.getSecurityCode() ).isEqualTo( card.getSecurityCode() );
        assertThat( cardDTO.getPrintedName() ).isEqualTo( card.getPrintedName() );
        assertThat( cardDTO.getId() ).isNotNull();

    }

    @Test
    @DisplayName( "deve lancar erro ao salvar cartao que ja existe" )
    public void deveLancarErroAoSalvarCartaoExistente() throws EntityExistValidateException {
        User user = UserUtils.createValidUser();

        when( userService.getById( 1L ) ).thenReturn( user );

        Card card = generateValidCard();

        when( cardRepository.findCardByCardNumberAndUser( card.getCardNumber(), user.getId() ) )
                .thenReturn( Optional.ofNullable( Card.builder()
                        .id( 1L )
                        .cardNumber( card.getCardNumber() )
                        .expirationDate( card.getExpirationDate() )
                        .securityCode( card.getSecurityCode() )
                        .printedName( card.getPrintedName() )
                        .user( user )
                        .build() ) );

        Throwable t = assertThrows( EntityExistValidateException.class,
                () -> cardService.isCardsExists( card.getCardNumber(), user.getId() ) );

        assertTrue( t.getMessage().contains( "Cartão já cadastrado." ) );

    }

    @Test
    @DisplayName( "deve lancar erro ao tentar salvar cartao usuario inexistente" )
    public void deveLancarErroUsuarioInexistente() throws UsernameNotFoundException {

        Long idUserNotExist = 999L;

        when( userService.getById( idUserNotExist ) ).thenReturn( null );

        Card card = generateValidCard();

        Throwable t = assertThrows( UsernameNotFoundException.class,
                () -> cardService.save( card, idUserNotExist ) );

        assertTrue( t.getMessage().contains( "Usuário não encontrado para o id: " + idUserNotExist) );

    }

    @Test
    @DisplayName( "deve consultar por id cartao valido" )
    public void deveConsultarPorIdValido() {

        Long idCard = 1L;

        Card card = generateValidCard();

        when( cardRepository.findById( idCard ) ).thenReturn(
                Optional.ofNullable( Card.builder()
                        .id( idCard )
                        .cardNumber( card.getCardNumber() )
                        .expirationDate( card.getExpirationDate() )
                        .printedName( card.getPrintedName() )
                        .securityCode( card.getSecurityCode() )
                        .build() ) );

        CardDTO cardDTO = cardService.getByIdCard( idCard );

        assertThat( cardDTO ).isNotNull();
        assertThat( cardDTO.getCardNumber() ).isEqualTo( card.getCardNumber() );
        assertThat( cardDTO.getSecurityCode() ).isEqualTo( card.getSecurityCode() );
        assertThat( cardDTO.getPrintedName() ).isEqualTo( card.getPrintedName() );
        assertThat( cardDTO.getId() ).isNotNull();
    }

    @Test
    @DisplayName( "deve consultar por id cartao nvalido" )
    public void deveConsultarPorIdInvalido() {

        Long idCard = 1L;

        Throwable t = assertThrows( EntityNotFoundException.class,
                () -> cardService.getByIdCard( idCard ) );

        assertTrue( t.getMessage().contains( String.format( "Cartão com identificador [%d] não encontrado.", idCard ) ) );

    }

    @Test
    @DisplayName( "deve listar cartoes pelo id usuario" )
    public void deveListarCartoesPeloIdUsuario() {

        List< Card > cardList = new ArrayList<>();

        User user = UserUtils.createValidUser();
        Card card = generateValidCard();

        card.setUser( user );
        card.setId( 1L );

        cardList.add( card );

        when( cardRepository.findCardsByUserId( user.getId() ) )
                .thenReturn( cardList );

        List< CardDTO > cardDTOList = cardService.getAll( user.getId() );

        assertThat( cardDTOList ).isNotNull();

    }

    @Test
    @DisplayName( "deve lancar erro ao listar cartoes de usuario que nao possui" )
    public void deveLancarErroUsuarioNaoPossuiCartoes() throws EntityNotFoundException {

        User user = UserUtils.createValidUser();

        when( cardRepository.findCardsByUserId( user.getId() ) )
                .thenReturn( null );

        Throwable t = assertThrows( EntityNotFoundException.class,
                () -> cardService.getAll( user.getId() ) );

        assertTrue( t.getMessage().contains( "Nenhum cartão encontrado para o usuário." ) );

    }

    @Test
    @DisplayName( "deve deletar pelo id cartao valido" )
    public void deveDeletarComSucesso() {

        Card card = generateValidCard();
        card.setId( 1L );

        when( cardRepository.findById( card.getId() ) )
                .thenReturn( Optional.of( card ) );

        MessageResponse messageResponse = cardService.delete( card.getId() );

        assertTrue( messageResponse.getMessage().contains( "Cartão excluído com sucesso." ) );

    }

    @Test
    @DisplayName( "deve lancar erro ao deletar cartão inexistente" )
    public void deveLancarErroAoDeletarCartao() {

        Long idCard = 1L;

        Throwable t = assertThrows( EntityNotFoundException.class, () -> cardService.delete( idCard ) );

        assertTrue( t.getMessage().contains( String.format( "Cartão com identificador [%d] não encontrado.", idCard ) ) );

    }

    private static Card generateValidCard() {
        return Card.builder()
                .cardNumber( "1234567812345678" )
                .securityCode( "999" )
                .printedName( "Teste do Teste" )
                .expirationDate( "25/25/2025" )
                .build();
    }

}