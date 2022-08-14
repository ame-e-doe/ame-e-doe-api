package com.api.loveanddonateapi.repository;

import com.api.loveanddonateapi.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Card findByUserId(Long userID);

    @Override
    Optional<Card> findById(Long id);

}
