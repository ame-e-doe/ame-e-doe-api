package br.com.loveanddonateapi.repository;

import br.com.loveanddonateapi.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository< Role, Long > {

    @Override
    Role save( Role role );

    Optional< Role > findByName( String name );
}
