package br.com.loveanddonateapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Data
@Builder
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
