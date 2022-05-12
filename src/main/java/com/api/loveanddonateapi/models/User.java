package com.api.loveanddonateapi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "USERS" )
public class User implements UserDetails {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    @Column( name = "email" )
    private String email;

    @NotBlank
    private String password;

    private Boolean locked = false;
    private Boolean enabled = false;

    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable( name = "user_roles",
                joinColumns = @JoinColumn( name = "id_user" ),
                inverseJoinColumns = @JoinColumn( name = "id_role" ) )
    private List<Role> roles;

    public User( String name,
                 String email,
                 String password ) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public List< Role > getRoles() {
        return this.roles;
    }

    public void setRoles( List<Role> roles ) {
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

}