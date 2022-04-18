package com.api.loveanddonateapi.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table( name = "users" )
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String name;

    @NotBlank
    @Email
    @Column( name = "email", unique = true)
    private String email;

    @NotBlank
    private String password;

    private Boolean locked = false;
    private Boolean enabled = false;

    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable( name = "user_roles",
                joinColumns = @JoinColumn( name = "user_id" ),
                inverseJoinColumns = @JoinColumn( name = "role_id" ) )
    private Set<Role> roles;

    public User( String email,
                 String password ) {
        this.email = email;
        this.password = password;
    }

    public Set< Role > getRoles() {
        return this.roles;
    }

    public void setRoles( Set< Role > roles ) {
        this.roles = roles;
    }

    @Override
    public Collection< ? extends GrantedAuthority > getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( !( o instanceof User ) ) return false;
        User user = ( User ) o;
        return Objects.equals( getId(), user.getId() ) && Objects.equals( getName(), user.getName() ) && Objects.equals( getEmail(), user.getEmail() ) && Objects.equals( getPassword(), user.getPassword() ) && Objects.equals( isAccountNonExpired(), user.isAccountNonExpired() ) && Objects.equals( isAccountNonLocked(), user.isAccountNonLocked() ) && Objects.equals( isCredentialsNonExpired(), user.isCredentialsNonExpired() ) && Objects.equals( isEnabled(), user.isEnabled() ) && Objects.equals( getRoles(), user.getRoles() );
    }

    @Override
    public int hashCode() {
        return Objects.hash( getId(), getName(), getEmail(), getPassword(), isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired(), isEnabled(), getRoles() );
    }
}