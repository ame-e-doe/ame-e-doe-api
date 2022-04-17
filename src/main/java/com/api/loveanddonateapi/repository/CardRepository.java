package com.api.loveanddonateapi.repository;

import com.api.loveanddonateapi.domain.Card;
import com.api.loveanddonateapi.dto.CardDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository< Card, Long > {

    List< Card > findAllCardsByUserId( Long userID );
}
