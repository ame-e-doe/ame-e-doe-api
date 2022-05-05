package com.api.loveanddonateapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "ID_CART", nullable = false )
    private Long id;

    @Column( name = "DATE_CART" )
    private LocalDateTime date = LocalDateTime.now();

    @ManyToMany
    @JoinTable( name = "CART_PRODUCTS",
            joinColumns = @JoinColumn( name = "ID_CART" ),
            inverseJoinColumns = @JoinColumn( name = "ID_PRODUCT" ) )
    @ToString.Exclude
    private Set< DigitalProduct > products = new HashSet<>();

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || Hibernate.getClass( this ) != Hibernate.getClass( o ) ) return false;
        Cart cart = ( Cart ) o;
        return id != null && Objects.equals( id, cart.id );
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
