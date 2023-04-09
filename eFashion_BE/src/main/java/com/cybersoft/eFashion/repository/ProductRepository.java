package com.cybersoft.eFashion.repository;

import com.cybersoft.eFashion.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products,Integer> {

}
