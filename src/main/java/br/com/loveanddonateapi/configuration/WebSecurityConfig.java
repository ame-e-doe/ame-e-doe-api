package br.com.loveanddonateapi.configuration;

import br.com.loveanddonateapi.service.UserService;
import br.com.loveanddonateapi.security.jwt.AuthEntryPointJwt;
import br.com.loveanddonateapi.security.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

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
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );
        http.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll());
        http.addFilterBefore( authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class );
    }

}
