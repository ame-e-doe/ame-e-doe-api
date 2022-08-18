package br.com.api.loveanddonateapi.repository;

import br.com.api.loveanddonateapi.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository< Category, Long > {
}