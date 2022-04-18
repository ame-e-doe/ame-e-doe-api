package com.api.loveanddonateapi.models;

import com.api.loveanddonateapi.models.enums.ERole;
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
    private Integer id;

    @Enumerated( EnumType.STRING )
    @Column( length = 20 )
    private ERole name;

    public Role( ERole name ) {
        this.name = name;
    }

    public ERole getName() {
        return name;
    }

    public void setName( ERole name ) {
        this.name = name;
    }
}
