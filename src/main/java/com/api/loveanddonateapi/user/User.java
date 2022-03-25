package com.api.loveanddonateapi.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @SequenceGenerator(
            name = "user",
            sequenceName = "user",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user"
    )
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated( EnumType.STRING )
    private UserRole userRole;
    private Boolean locked;
    private Boolean enabled;

    public User( String email,
                 String password,
                 UserRole userRole
    ) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    @Override
    public Collection< ? extends GrantedAuthority > getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority =
                new SimpleGrantedAuthority( userRole.name() );
        return Collections.singletonList( simpleGrantedAuthority );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
