package com.navita.product.ms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.navita.product.ms.models.ProductModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductModel, UUID>{
	
    @Query("SELECT p FROM ProductModel p WHERE p.name LIKE %?1%"
            + " OR p.description LIKE %?1%"
            + " OR CONCAT(p.price, '') LIKE %?1%")
    public List<ProductModel> search(String keyword);
    
    @Query("SELECT p FROM ProductModel p WHERE p.name LIKE %?1%")
    public ProductModel findByName(String name);

}
