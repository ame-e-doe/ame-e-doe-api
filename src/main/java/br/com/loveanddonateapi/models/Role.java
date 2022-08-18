package br.com.loveanddonateapi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table( name = "ROLES" )
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( name = "role_description", length = 20 )
    private String name;

    public Role( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }
}
