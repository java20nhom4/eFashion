package com.cybersoft.eFashion.service;

import com.cybersoft.eFashion.dto.CategoryDTO;
import com.cybersoft.eFashion.entity.Category;
import com.cybersoft.eFashion.repository.CategoryRepository;
import com.cybersoft.eFashion.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements CategoryServiceImp{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public boolean addCatergory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        try {
            categoryRepository.save(category);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteCatergory(long id) {
//        Category category = new Category();
//        category.setId(id);

        //Tim id gan vao category.setId
        try{
            categoryRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    // public boolean deleteCategoryByNameAndDescription
    @Override
    public boolean updateCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category = categoryRepository.findById(categoryDTO.getId());// Tìm id trong đối tượng category có 3 tham số
        category.setName(categoryDTO.getName());//set name theo DTO truyền vào
        category.setDescription(categoryDTO.getDescription());

        try {
            categoryRepository.save(category);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public CategoryDTO getCategoryById(long id) {
        Category category = categoryRepository.findById(id);
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(category.getName());
        categoryDTO.setId(category.getId());
        categoryDTO.setDescription(category.getDescription());

        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> list = categoryRepository.findAll();
        List<CategoryDTO> dtoList = new ArrayList<>();
        for (Category category : list){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setDescription(category.getDescription());
            categoryDTO.setName(category.getName());

            dtoList.add(categoryDTO);
        }
        return dtoList;
    }




}
