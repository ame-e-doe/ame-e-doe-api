package com.api.loveanddonateapi.repository;

import com.api.loveanddonateapi.models.Role;
import com.api.loveanddonateapi.models.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository< Role, Long > {

    Optional< Role > findByName( ERole name );
}
