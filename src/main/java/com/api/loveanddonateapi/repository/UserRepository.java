package com.api.loveanddonateapi.repository;

import com.api.loveanddonateapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository< User, Long> {

    Optional< User > findByEmail( String email );


    Boolean existsByEmail( String email );

    @Transactional
    @Modifying
    @Query( "UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = ?1" )
    int enableUser( String email );

    @Transactional
    @Query( "SELECT e.enabled FROM User e WHERE e.email = ?1" )
    Boolean isEnabled( String email );

}
