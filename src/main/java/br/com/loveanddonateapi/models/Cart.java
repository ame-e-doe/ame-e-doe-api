package br.com.loveanddonateapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @OneToOne
    @JoinColumn(name = "USER_ID")
    //@JsonIgnore
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItem;

    @Column(name = "VALOR_TOTAL_CART")
    private Double totalPrice;

}
