package com.cybersoft.eFashion.service.imp;

import com.cybersoft.eFashion.dto.OrderItemsDTO;
import com.cybersoft.eFashion.dto.ProductsDTO;
import com.cybersoft.eFashion.entity.Products;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductsServiceImp {
    boolean addProduct(MultipartFile file, ProductsDTO productDTO);

    List<ProductsDTO> getProductByCateId(int id);

    boolean editProduct(MultipartFile file, ProductsDTO productDTO);

}
