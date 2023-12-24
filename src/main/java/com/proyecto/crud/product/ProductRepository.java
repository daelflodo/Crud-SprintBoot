package com.proyecto.crud.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    //Ejmplo Query
    //@Query("SELECT * FROM Product p WHERE p.name= ?1")
    Optional<Product> findProductByName(String name);
}
