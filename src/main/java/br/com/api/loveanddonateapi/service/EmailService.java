package br.com.api.loveanddonateapi.service;

import br.com.api.loveanddonateapi.models.email.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class EmailService implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger( EmailService.class );

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    @Async
    public void send( String to, String email ){
        log.debug( "Send email for user by email {}", email );
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper( mimeMessage, "utf-8" );
            mimeMessageHelper.setText( email, true );
            mimeMessageHelper.setTo( to );
            mimeMessageHelper.setSubject( "Confirme seu e-mail." );
            mimeMessageHelper.setFrom( "love@love.com" );
            javaMailSender.send( mimeMessage );
        } catch( MessagingException e ) {
            LOGGER.error( "Falha ao enviar e-mail", e );
            throw new IllegalStateException( "Falha ao enviar e-mail") ;
        }
    }


}