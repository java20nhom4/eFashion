package com.cybersoft.eFashion.service;

import com.cybersoft.eFashion.dto.ProductsDto;
import com.cybersoft.eFashion.entity.Category;
import com.cybersoft.eFashion.entity.Products;
import com.cybersoft.eFashion.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Convert entity to dto
    private ProductsDto mapEntityToDto(Products entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, ProductsDto.class);
    }

    // Convert dto to entity
    private Products mapDtoToEntity(ProductsDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Products.class);
    }

    // Get all products
    public List<ProductsDto> findAll() {
        List<Products> entities = productRepository.findAll();
        return entities.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    // Get product by id
    public ProductsDto findById(int id) {
        Products entity = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
        return mapEntityToDto(entity);
    }

    @Transactional
    // Create or update product
    public void save(ProductsDto dto) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(ProductsDto.class,Products.class).addMappings(mapper->{
            mapper.<Long>map(ProductsDto::getCategoryId,(dest,value)->dest.getCategory().setId(value)
            );
        });

        Products entity = modelMapper.map(dto,Products.class);
        productRepository.save(entity);
    }

    // Delete product by id
    public void delete(int id) {
        Products entity = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
        productRepository.delete(entity);


    }
}