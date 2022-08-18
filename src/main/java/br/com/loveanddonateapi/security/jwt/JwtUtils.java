package br.com.loveanddonateapi.security.jwt;

import br.com.loveanddonateapi.service.UserService;
import br.com.loveanddonateapi.exception.InvalidJwtAuthenticationException;
import br.com.loveanddonateapi.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    @Value( "${lovesecurity.app.jwtSecret}" )
    private String jwtSecret;

    @Value( "${lovesecurity.app.jwtExpirationMs}" )
    private long jwtExpirationMs;

    @Autowired
    UserService userService;

    @PostConstruct
    public void init() {
        jwtSecret = Base64.getEncoder().encodeToString( jwtSecret.getBytes() );
    }

    public String generateJwtToken( Authentication authentication ) {
        User userPrincipal = ( User ) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject( userPrincipal.getUsername() )
                .setIssuedAt( new Date() )
                .setExpiration( new Date(new Date().getTime() + jwtExpirationMs ) )
                .signWith( SignatureAlgorithm.HS256, jwtSecret )
                .compact();
    }

    public String getUserNameFromJwtToken( String token ) {
        return Jwts.parser().setSigningKey( jwtSecret ).parseClaimsJws( token ).getBody().getSubject();
    }

    public String parseJwt( HttpServletRequest request ) {
        String bearerToken = request.getHeader( "Authorization" );
        if( StringUtils.hasText( bearerToken ) && bearerToken.startsWith( "Bearer " ) ) {
            return bearerToken.substring( 7, bearerToken.length() );
        }
        return null;
    }

    public boolean validateJwtToken( String authToken ) {
        try {
            Jws< Claims > claims = Jwts.parser().setSigningKey( jwtSecret ).parseClaimsJws( authToken );
            if( claims.getBody().getExpiration().before( new Date() ) ) {
                return false;
            }
            return true;
        } catch ( Exception e ) {
            throw new InvalidJwtAuthenticationException( "Expired or invalid token" );
        }
    }

}
