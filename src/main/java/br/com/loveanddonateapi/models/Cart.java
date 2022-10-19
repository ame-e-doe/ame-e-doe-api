package br.com.loveanddonateapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CART")
public class Cart {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(
            nullable = false,
            name = "id_user")
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItem;

    @Column(name = "VALOR_TOTAL_CART")
    private Double totalPrice;

    public Cart( User user ) {
        this.user = user;
        this.totalPrice = 0d;

    }

}
