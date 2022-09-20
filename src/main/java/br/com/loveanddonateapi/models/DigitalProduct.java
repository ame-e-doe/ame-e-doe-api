package br.com.loveanddonateapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table( name = "DIGITAL_PRODUCT" )
public class DigitalProduct {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "ID_DIGI_PROD", nullable = false )
    private Long id;

    @OneToOne
    @JoinColumn( name = "ID_IMAGE" )
    private Image image;

    @Column( name = "TITLE_DIGI_PROD" )
    private String title;

    @Column( name = "DESC_DIGI_PROD" )
    private String description;

    @Column( name = "VALUE_DIGI_PROD" )
    private Double value;

    @ManyToOne
    @JoinColumn( name = "ID_CATEGORY" )
    private Category category;

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || Hibernate.getClass( this ) != Hibernate.getClass( o ) ) return false;
        DigitalProduct that = ( DigitalProduct ) o;
        return id != null && Objects.equals( id, that.id );
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
