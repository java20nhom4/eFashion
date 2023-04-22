package com.cybersoft.eFashion.repository;

import com.cybersoft.eFashion.entity.RatingProducts;
import com.cybersoft.eFashion.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingProducts, Integer> {
    List<RatingProducts> findAll();
    List<RatingProducts> findByProducts_Id(int product_id);
    List<RatingProducts> findByUsers_Id(int user_id);
    RatingProducts getById(int rating_id);
    Integer deleteById(int rating_id);

    @Query("SELECT coalesce(max(id), 0) FROM rating_product")
    int getMaxId();
}
