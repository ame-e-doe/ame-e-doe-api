package com.api.loveanddonateapi.models;

import com.api.loveanddonateapi.models.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sale {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "ID_SALE", nullable = false )
    private Long id;

    @Column( name = "DATA_PEDIDO" )
    private LocalDateTime date = LocalDateTime.now();

    @ManyToMany
    @JoinTable( name = "SALE_PRODUCTS",
            joinColumns = @JoinColumn( name = "ID_SALE" ),
            inverseJoinColumns = @JoinColumn( name = "ID_PRODUCT" ) )
    @ToString.Exclude
    private Set< DigitalProduct > products = new HashSet<>();

    @ManyToOne
    @JoinColumn( name = "USER_ID" )
    private User user;

    @Column( name = "VALUE_ORDER" )
    private Double value;

    @Column( name = "STATUS_PAYMENT" )
    private PaymentStatus status;

}
