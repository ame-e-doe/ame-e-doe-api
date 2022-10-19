package br.com.loveanddonateapi.repository;

import br.com.loveanddonateapi.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Override
    Cart save( Cart cart );

    Optional<Cart> getCartsByUserId( Long idUser );

}
