package com.api.loveanddonateapi.domain;

import com.api.loveanddonateapi.domain.enums.UserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated( EnumType.STRING )
    private UserRole userRole;
    private Boolean locked = false;
    private Boolean enabled = false;

    @ManyToMany
    @JoinTable( name = "USER_PRODUCTS",
            joinColumns = @JoinColumn( name = "ID_USER" ),
            inverseJoinColumns = @JoinColumn( name = "ID_PRODUCT" ) )
    @ToString.Exclude
    private Set< DigitalProduct > products = new HashSet<>();

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
