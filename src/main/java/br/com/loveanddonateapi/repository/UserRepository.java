package br.com.loveanddonateapi.repository;

import br.com.loveanddonateapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository< User, Long> {

    @Override
    User save( User user );

    User findByUsername( String username );

    Boolean existsByUsername( String username );

}
