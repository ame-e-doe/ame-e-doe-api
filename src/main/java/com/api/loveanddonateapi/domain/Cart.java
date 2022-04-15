package com.api.loveanddonateapi.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
@Table(name = "CART")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CART", nullable = false)
    private Long id;

    @Column(name = "DATE_CART")
    private LocalDateTime date = LocalDateTime.now();

    @ManyToMany
    @JoinTable(name="CART_PRODUCTS",
            joinColumns = @JoinColumn (name = "ID_PRODUCT"),
            inverseJoinColumns = @JoinColumn (name = "ID_CART"))
    @ToString.Exclude
    private Set<DigitalProduct> products = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cart cart = (Cart) o;
        return id != null && Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
