package br.com.api.loveanddonateapi.repository;

import br.com.api.loveanddonateapi.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository< Sale, Long > {

    List<Sale> findAllByUserId(Long userId);
}
