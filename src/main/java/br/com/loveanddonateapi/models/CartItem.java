package br.com.loveanddonateapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CART_ITEM")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CART_ITEM")
    private Long id;

    @Column(name = "QUANTIDADE_CART_ITEM")
    private Integer quantity;

    @Column(name = "PRECO_CART_ITEM")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "ID_DIGI_PROD")
    private DigitalProduct product;

    @ManyToOne
    @JoinColumn(name = "ID_CART")
    @JsonIgnore
    private Cart cart;

}
