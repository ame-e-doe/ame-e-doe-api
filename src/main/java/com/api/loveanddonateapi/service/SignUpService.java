package com.api.loveanddonateapi.service;

import com.api.loveanddonateapi.dto.response.MessageResponse;
import com.api.loveanddonateapi.dto.signup.SignUpDTO;
import com.api.loveanddonateapi.models.ConfirmationToken;
import com.api.loveanddonateapi.models.Role;
import com.api.loveanddonateapi.models.User;
import com.api.loveanddonateapi.models.email.EmailSender;
import com.api.loveanddonateapi.models.enums.ERole;
import com.api.loveanddonateapi.repository.RoleRepository;
import com.api.loveanddonateapi.repository.UserRepository;
import com.api.loveanddonateapi.utils.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SignUpService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @Autowired
    EmailSender emailSender;
    private User user;

    public ResponseEntity< ? > signUp( SignUpDTO signUpDTO ) {

        log.debug( "Start validation for user exists {}", signUpDTO.getEmail() );
        if( userRepository.existsByEmail( signUpDTO.getEmail() ) ) {
            return ResponseEntity
                    .badRequest()
                    .body( new MessageResponse( "Erro: O usuário já existe!" ) );
        }

        log.debug( "User non exists proceed register {}", signUpDTO.getEmail() );
        User user = new User(
                signUpDTO.getName(),
                signUpDTO.getEmail(),
                passwordEncoder.encode( signUpDTO.getPassword() ) );

        String strRoles = ( signUpDTO.getRole() );
        List< Role > roles = new ArrayList<>();

        if( strRoles == null ) {
            Role userRole = roleRepository.findByName( ERole.ROLE_USER.name() )
                    .orElseThrow( () -> new RuntimeException( "Error: Role is not found" ) );
            roles.add( userRole );
        }

        user.setRoles( roles );
        log.debug( "Save user in database {}", signUpDTO.getEmail() );
        userRepository.save( user );

        confirmationTokenService.saveConfirmationToken( generateConfirmationToken( user ) );

        return ResponseEntity.ok( new MessageResponse(
                EmailUtils
                        .formatterEmail( signUpDTO.getEmail() ) ) );
    }

    private ConfirmationToken generateConfirmationToken( User user ) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken( token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes( 15 ),
                user );
        log.debug( "Send email for this user {}", user.getEmail() );
        sendEmail( user.getEmail(), token );

        return confirmationToken;
    }

    private void sendEmail( String email, String token ) {
        String link = "http://localhost:8080/api/auth/confirm?token=" + token;
        emailSender.send( email, buildEmail( email, link ) );
    }

    @Transactional
    public ResponseEntity< ? > confirmToken( String token ) {
        log.debug( "Validate token in database {}", token );
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken( token )
                .orElseThrow( () ->
                        new RuntimeException( "Token não encontrado" ) );

        log.debug( "Validate in database token already confirmed {}", token );
        if( confirmationToken.getConfirmedAt() != null ) {
            return ResponseEntity
                    .badRequest()
                    .body( new MessageResponse( "Email já confirmado!" ) );
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        log.debug( "Validate token has ben expired {}", token );
        if( expiredAt.isBefore( LocalDateTime.now() ) ) {
            Optional< ConfirmationToken > user = confirmationTokenService.getToken( token );
            User test = user.get().getUser();
            confirmationTokenService.saveConfirmationToken( generateConfirmationToken( test ) );
            return ResponseEntity
                    .badRequest()
                    .body( new MessageResponse( "Token expirado, um novo e-mail foi enviado!" ) );
        }

        log.debug( "Update status token in database and enabled user {}", token );
        confirmationTokenService.setConfirmedAt( token );
        userService.enableUser(
                confirmationToken.getUser().getEmail() );
        return ResponseEntity
                .ok()
                .body( new MessageResponse( "E-mail confirmado com sucesso!" ) );
    }

    private String buildEmail( String name, String link ) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}