package br.com.loveanddonateapi.repository;

import br.com.loveanddonateapi.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT c FROM Cart c WHERE c.user.id = :userId")
    Cart getCartByUserId(Long userId);

}