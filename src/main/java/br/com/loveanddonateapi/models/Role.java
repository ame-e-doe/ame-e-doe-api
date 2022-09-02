package br.com.loveanddonateapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table( name = "ROLES" )
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( name = "role_description", length = 20 )
    private String name;

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
