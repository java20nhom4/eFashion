package com.cybersoft.eFashion.controller;

import com.cybersoft.eFashion.dto.ProductsDTO;
import com.cybersoft.eFashion.payload.ResponseData;
import com.cybersoft.eFashion.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductController {

    int idProductDetail = 0;

    @Autowired
    private ProductService productService;

    // GET all products
    @GetMapping("/getAll")
    public ResponseEntity<Object> findAll() {
        List<ProductsDTO> products = productService.findAll();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/getProductByCateId")
    public ResponseEntity<Object> getProductByCateId(@RequestParam int cateId) {
        List<ProductsDTO> products = productService.getProductByCateId(cateId);
        return ResponseEntity.ok(products);
    }
    @PostMapping("/postIdProductDetail")
    public ResponseEntity<?> postIdProductDetail(@RequestParam int id) {
        ResponseData responseData = new ResponseData();
        idProductDetail = id;

        if (idProductDetail != 0) {
            responseData.setData(idProductDetail);
            responseData.setStatusCode(200);
        } else {
            responseData.setData(idProductDetail);
            responseData.setStatusCode(400);
        }
        return new ResponseEntity<>(idProductDetail, HttpStatus.OK);
    }

    // GET product by id
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable int id) {
        ProductsDTO product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    // POST create new product
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ProductsDTO dto) {
        productService.save(dto, null);
        return ResponseEntity.ok("Product created successfully");
    }

//     PUT update product by id
//    @PostMapping("/edit")
//    public ResponseEntity<Object> update(@RequestParam int pro_id,
//                                         @RequestParam String pro_name,
//                                         @RequestParam Double pro_price,
//                                         @RequestParam String pro_des,
//                                         @RequestParam int pro_quant,
//                                         @RequestParam MultipartFile image,
//                                         @RequestParam String pro_status,
//                                         @RequestParam Long pro_cate) {
//        ProductsDTO productsDTO = new ProductsDTO();
//        productsDTO.setId(pro_id);
//        productsDTO.setName(pro_name);
//        productsDTO.setPrice(pro_price);
//        productsDTO.setDescription(pro_des);
//        productsDTO.setQuantity(pro_quant);
//        productsDTO.setImage(image.getName());
//        productsDTO.setCategoryId(pro_cate);
//        productsDTO.setStatus(pro_status);
//        productService.save(productsDTO, image);
//        return ResponseEntity.ok("Product updated successfully");
//    }

    // DELETE delete product by id
    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        productService.delete(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addProduct(@RequestParam("image") MultipartFile file,
                                        @RequestParam String pro_name,
                                        @RequestParam double pro_price,
                                        @RequestParam String pro_des,
                                        @RequestParam int pro_quant,
                                        @RequestParam String pro_status,
                                        @RequestParam long pro_cate){
        ResponseData responseData = new ResponseData();
        ProductsDTO product = new ProductsDTO();
        product.setName(pro_name);
        product.setPrice(pro_price);
        product.setDescription(pro_des);
        product.setQuantity(pro_quant);
        product.setStatus(pro_status);
        product.setCategoryId(pro_cate);
        responseData.setData(productService.addProduct(file, product));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    
    @PostMapping(value = "/edit")
    public ResponseEntity<?> editProduct(@RequestParam("image") MultipartFile file,
                                         @RequestParam int pro_id,
                                         @RequestParam String pro_name,
                                        @RequestParam double pro_price,
                                        @RequestParam String pro_des,
                                        @RequestParam int pro_quant,
                                        @RequestParam String pro_status,
                                        @RequestParam long pro_cate){
        ResponseData responseData = new ResponseData();
        ProductsDTO product = new ProductsDTO();
        System.out.println("hello edit");
        product.setId(pro_id);
        product.setName(pro_name);
        product.setPrice(pro_price);
        product.setDescription(pro_des);
        product.setQuantity(pro_quant);
        product.setStatus(pro_status);
        product.setCategoryId(pro_cate);
        responseData.setData(productService.editProduct(file, product));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}

