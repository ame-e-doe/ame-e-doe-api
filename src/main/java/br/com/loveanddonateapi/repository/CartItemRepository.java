package br.com.loveanddonateapi.repository;

import br.com.loveanddonateapi.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Override
    CartItem save( CartItem cartItem );

    @Modifying
    @Query(value = "DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteAllByIdCart(Long cartId);

}
