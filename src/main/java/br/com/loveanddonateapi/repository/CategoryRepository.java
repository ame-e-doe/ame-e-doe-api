package br.com.loveanddonateapi.repository;

import br.com.loveanddonateapi.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

     Category findAllById( Long id );

}