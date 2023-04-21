package com.cybersoft.eFashion.service.imp;

import com.cybersoft.eFashion.dto.CategoryDTO;

import java.util.List;

public interface CategoryServiceImp {

    boolean addCatergory(CategoryDTO categoryDTO);
    boolean deleteCatergory(long id);
    boolean updateCategory(CategoryDTO categoryDTO);
    CategoryDTO getCategoryById(long id);
    List<CategoryDTO> getAllCategory();
}
