package com.cybersoft.eFashion.service;

import com.cybersoft.eFashion.dto.ProductsDto;
import com.cybersoft.eFashion.dto.RatingDTO;
import com.cybersoft.eFashion.entity.Category;
import com.cybersoft.eFashion.entity.Products;
import com.cybersoft.eFashion.entity.RatingProducts;
import com.cybersoft.eFashion.entity.Users;
import com.cybersoft.eFashion.repository.CategoryRepository;
import com.cybersoft.eFashion.repository.ProductRepository;
import com.cybersoft.eFashion.service.imp.FileStorageServiceImp;
import com.cybersoft.eFashion.service.imp.ProductsServiceImp;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements ProductsServiceImp {
    @Value("${fileStorage.path}")
    private String parentFolder;

    @Autowired
    FileStorageServiceImp fileStorageServiceImp;

    @Autowired
    CategoryRepository categoryRepository;

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
        for(Products pro: entities){
            pro.setImage(parentFolder + "\\" + FolderType.Products.toString() + "\\" + pro.getImage());
        }
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

    @Override
    public boolean addProduct(MultipartFile file, ProductsDto productDTO) {
        boolean isInsertSuccess = false;
        boolean isSaveFileSuccess = true;
        int idImage = productRepository.getMaxId() + 1;
        String newFileName = "";
        // Save Image First
        if (file != null){
            // Set name image as format: "id_cateId_time.typeFile"
            String now = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            newFileName = idImage + "_" + productDTO.getCategoryId()  + "_" + now  + "." + extension;
            isSaveFileSuccess = fileStorageServiceImp.saveFiles(file, newFileName, FolderType.Products);
        }
        // Insert then
        if (isSaveFileSuccess){
            Category cate = categoryRepository.getCategoryById(productDTO.getCategoryId());
            if (cate == null){
                isInsertSuccess = false;
                System.out.println("Get null category, please add category first");
            }
            else{
                Products product = new Products();
                product.setCategory(cate);
                product.setName(productDTO.getName());
                product.setPrice(productDTO.getPrice());
                product.setDescription(productDTO.getDescription());
                product.setQuantity(productDTO.getQuantity());
                product.setStatus(productDTO.getStatus());
                product.setImage(newFileName);
                productRepository.save(product);
                isInsertSuccess = true;
            }
        }
        System.out.println("Add product: " + isInsertSuccess);
        return isInsertSuccess;
    }
}