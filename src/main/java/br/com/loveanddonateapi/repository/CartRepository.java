package br.com.loveanddonateapi.repository;

import br.com.loveanddonateapi.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT c FROM Cart c WHERE c.user.id = :userId")
    Optional<Cart> getCartByUserId(Long userId);

}
