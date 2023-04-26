package com.cybersoft.eFashion.repository;

import com.cybersoft.eFashion.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products,Integer> {
    @Query("SELECT coalesce(max(id), 0) FROM products")
    int getMaxId();
    Products getProductsById(int id);

    List<Products> findAll();
    @Query("select p from products p where cate_id=?1")
    List<Products> getProductsByCateId(int id);
}
