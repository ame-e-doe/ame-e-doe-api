package br.com.loveanddonateapi.configuration.security;

import br.com.loveanddonateapi.configuration.jwt.AuthEntryPointJwt;
import br.com.loveanddonateapi.configuration.jwt.AuthTokenFilter;
import br.com.loveanddonateapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userDetailsService;

    private final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure( AuthenticationManagerBuilder authenticationManagerBuilder ) throws Exception {
        authenticationManagerBuilder.userDetailsService( userDetailsService ).passwordEncoder( passwordEncoder() );
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable();

        http.exceptionHandling().authenticationEntryPoint( unauthorizedHandler );
        http.sessionManagement().sessionCreationPolicy( STATELESS );
        http.authorizeRequests().antMatchers( "/api/user/register", "/api/auth/login", "/api/products/**", "/api/category/**", "/api/images/**" ).permitAll();

//        Endpoint de test
        http.authorizeRequests().antMatchers( "/api/test/all" ).permitAll();

        http.authorizeRequests().antMatchers().hasAnyAuthority( "ROLE_USER" );
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore( authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class );
    }

}