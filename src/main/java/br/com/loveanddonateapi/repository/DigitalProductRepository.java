package br.com.loveanddonateapi.repository;

import br.com.loveanddonateapi.models.Category;
import br.com.loveanddonateapi.models.DigitalProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DigitalProductRepository extends JpaRepository< DigitalProduct, Long > {

    List< DigitalProduct > getDigitalProductsByCategory( Category category );

    @Query( "SELECT p FROM DigitalProduct p WHERE p.title LIKE %:search% OR p.description LIKE %:search%" )
    List< DigitalProduct > findDigitalProductsByTitleOrDescription( String search );

}
