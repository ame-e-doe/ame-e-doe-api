package com.api.loveanddonateapi.domain;

import com.api.loveanddonateapi.domain.enums.ERole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table( name = "roles" )
public class Role {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Enumerated( EnumType.STRING )
    @Column( length = 20 )
    private ERole name;

    public Role( ERole name ) {
        this.name = name;
    }

    public ERole getRole() {
        return name;
    }

    public void setName( ERole name ) {
        this.name = name;
    }
}
