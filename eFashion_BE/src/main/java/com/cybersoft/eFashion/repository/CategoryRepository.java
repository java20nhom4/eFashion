package com.cybersoft.eFashion.repository;

import com.cybersoft.eFashion.entity.Category;
import com.cybersoft.eFashion.entity.RatingProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByNameAndDescription(String name, String description);
    Category findById(long id);
    Category getCategoryById(long id);

}
