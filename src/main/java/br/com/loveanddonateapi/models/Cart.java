package br.com.loveanddonateapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "CART" )
public class Cart {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(
            nullable = false,
            name = "id_user" )
    private User user;

    @OneToMany( mappedBy = "cart" )
    private List< CartItem > listCartItens;

    @Column( name = "VALOR_TOTAL_CART" )
    private Double totalPrice;

    public Cart( User user ) {
        this.user = user;
        this.totalPrice = 0d;

    }

}
