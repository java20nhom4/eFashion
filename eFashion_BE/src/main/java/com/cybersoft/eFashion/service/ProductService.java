package com.cybersoft.eFashion.service;

import com.cybersoft.eFashion.dto.CategoryDTO;
import com.cybersoft.eFashion.dto.ProductsDTO;
import com.cybersoft.eFashion.entity.Category;
import com.cybersoft.eFashion.entity.Products;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ProductService implements ProductsServiceImp {
    @Value("${fileStorage.path}")
    private String parentFolder;

    @Autowired
    FileStorageServiceImp fileStorageServiceImp;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    // Convert entity to dto
    private ProductsDTO mapEntityToDto(Products entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, ProductsDTO.class);
    }

    // Convert dto to entity
    private Products mapDtoToEntity(ProductsDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Products.class);
    }

    // Get all products
    public List<ProductsDTO> findAll() {
        List<ProductsDTO> list = new ArrayList<>();
        List<Products> productsList = productRepository.findAll();
        for (Products products: productsList) {
            ProductsDTO productsDto = new ProductsDTO();
            productsDto.setId(products.getId());
            productsDto.setName(products.getName());
            productsDto.setPrice(products.getPrice());
            productsDto.setStatus(products.getStatus());
            String path  = parentFolder + "\\" + FolderType.Products.toString() + "\\" + products.getImage();
            productsDto.setImage(path);

            CategoryDTO categoryDTO = new CategoryDTO();
            Category category = categoryRepository.getCategoryById(products.getCategory().getId());
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());

            productsDto.setCategoryDTO(categoryDTO);

            list.add(productsDto);
        }

        return list;
    }

    // Get product by id
    public ProductsDTO findById(int id) {
        Products entity = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
        String path  = parentFolder + "\\" + FolderType.Products.toString() + "\\" + entity.getImage();
        entity.setImage(path);
        return mapEntityToDto(entity);
    }

    @Transactional
    // Create or update product
    public void save(ProductsDTO dto, MultipartFile image) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(ProductsDTO.class,Products.class).addMappings(mapper->{
            mapper.<Long>map(ProductsDTO::getCategoryId,(dest, value)->dest.getCategory().setId(value)
            );
        });

        Products entity = modelMapper.map(dto,Products.class);
        try {
            productRepository.save(entity);
            fileStorageServiceImp.saveFiles(image, image.getOriginalFilename(), FolderType.Products);
        } catch (Exception e) {
            System.out.println("error save product");
        }
    }

    // Delete product by id
    public void delete(int id) {
        Products entity = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
        productRepository.delete(entity);

    }

    @Override
    public boolean addProduct(MultipartFile file, ProductsDTO productDTO) {
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

    @Override
    public List<ProductsDTO> getProductByCateId(int id) {
        List<ProductsDTO> list = new ArrayList<>();
        List<Products> productsList = productRepository.getProductsByCateId(id);
        for (Products products : productsList) {
            ProductsDTO productsDto = new ProductsDTO();
            productsDto.setId(products.getId());
            productsDto.setName(products.getName());
            productsDto.setPrice(products.getPrice());
            productsDto.setStatus(products.getStatus());
            String path  = parentFolder + "\\" + FolderType.Products.toString() + "\\" + products.getImage();
            productsDto.setImage(path);

            CategoryDTO categoryDTO = new CategoryDTO();
            Category category = categoryRepository.getCategoryById(products.getCategory().getId());
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());

            productsDto.setCategoryDTO(categoryDTO);

            list.add(productsDto);
        }

        return list;
    }
    public boolean editProduct(MultipartFile file, ProductsDTO productDTO) {
        System.out.println("hello edit 2");
        boolean isEdittSuccess = false;
        boolean isSaveFileSuccess = true;
        int idImage = productRepository.getMaxId() + 1;
        String newFileName = "";
        // Save Image First
        if (file != null) {
            // Set name image as format: "id_cateId_time.typeFile"
            String now = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            newFileName = idImage + "_" + productDTO.getCategoryId() + "_" + now + "." + extension;
            isSaveFileSuccess = fileStorageServiceImp.saveFiles(file, newFileName, FolderType.Products);
        }
        // Insert then
        if (isSaveFileSuccess) {
            Category cate = categoryRepository.getCategoryById(productDTO.getCategoryId());
            if (cate == null) {
                isEdittSuccess = false;
                System.out.println("Get null category, please add category first");
            } else {
                Products product = productRepository.getProductsById(productDTO.getId());
                product.setCategory(cate);
                product.setName(productDTO.getName());
                product.setPrice(productDTO.getPrice());
                product.setDescription(productDTO.getDescription());
                product.setQuantity(productDTO.getQuantity());
                product.setStatus(productDTO.getStatus());
                product.setImage(newFileName);
                productRepository.save(product);
                isEdittSuccess = true;
            }
        }
        System.out.println("Edit product: " + isEdittSuccess);
        return isEdittSuccess;
    }
}