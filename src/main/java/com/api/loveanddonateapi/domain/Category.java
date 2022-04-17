package com.api.loveanddonateapi.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table( name = "CATEGORY" )
public class Category {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "ID_CAT", nullable = false )
    private Long id;

    @Column( name = "DESCRICAO_CAT" )
    private String description;

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || Hibernate.getClass( this ) != Hibernate.getClass( o ) ) return false;
        Category category = ( Category ) o;
        return id != null && Objects.equals( id, category.id );
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
