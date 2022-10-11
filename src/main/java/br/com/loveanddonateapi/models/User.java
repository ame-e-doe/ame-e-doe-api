package br.com.loveanddonateapi.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "USERS" )
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean locked = false;
    private Boolean enabled = false;

    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable( name = "user_roles",
                joinColumns = @JoinColumn( name = "id_user" ),
                inverseJoinColumns = @JoinColumn( name = "id_role" ) )
    private List<Role> roles;

    @OneToOne(
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = { CascadeType.ALL } )
    @JoinTable( name = "cart",
                joinColumns = @JoinColumn(name = "id_user"),
                inverseJoinColumns = @JoinColumn( name = "id") )
    private Cart cart;

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