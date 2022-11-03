package br.com.loveanddonateapi.repository;

import br.com.loveanddonateapi.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository< Card, Long > {

    @Override
    Card save( Card card );

    @Override
    Optional< Card > findById( Long id );

     List< Card > findCardsByUserId( Long idUser );

    @Query( value = "SELECT c FROM Card c WHERE c.cardNumber = :cardNumber AND c.user.id = :idUser" )
    Optional< Card > findCardByCardNumberAndUser( String cardNumber, Long idUser );

}