package br.com.api.loveanddonateapi.repository;

import br.com.api.loveanddonateapi.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository< Role, Long > {

    Optional< Role > findByName( String name );
}
