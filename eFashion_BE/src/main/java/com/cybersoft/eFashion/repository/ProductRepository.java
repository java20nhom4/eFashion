package com.cybersoft.eFashion.repository;

import com.cybersoft.eFashion.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products,Integer> {
    @Query("SELECT coalesce(max(id), 0) FROM products")
    int getMaxId();
}
