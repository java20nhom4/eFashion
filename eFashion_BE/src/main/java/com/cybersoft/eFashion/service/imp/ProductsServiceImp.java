package com.cybersoft.eFashion.service.imp;

import com.cybersoft.eFashion.dto.ProductsDto;
import org.springframework.web.multipart.MultipartFile;

public interface ProductsServiceImp {
    boolean addProduct(MultipartFile file, ProductsDto productDTO);
}
