package com.cybersoft.eFashion.controller;

import com.cybersoft.eFashion.dto.ProductsDto;
import com.cybersoft.eFashion.payload.ResponseData;
import com.cybersoft.eFashion.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    // GET all products
    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<ProductsDto> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    // GET product by id
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable int id) {
        ProductsDto product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    // POST create new product
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ProductsDto dto) {
        productService.save(dto);
        return ResponseEntity.ok("Product created successfully");
    }

    // PUT update product by id
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody ProductsDto dto) {
        dto.setId(id);
        productService.save(dto);
        return ResponseEntity.ok("Product updated successfully");
    }

    // DELETE delete product by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        productService.delete(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addProduct(@RequestParam("file") MultipartFile file,
                                        @RequestParam String pro_name,
                                        @RequestParam Double pro_price,
                                        @RequestParam String pro_des,
                                        @RequestParam Integer pro_quant,
                                        @RequestParam String pro_status,
                                        @RequestParam Long pro_cate){
        ResponseData responseData = new ResponseData();
        System.out.println("hello add product");
        ProductsDto product = new ProductsDto();
        product.setName(pro_name);
        product.setPrice(pro_price);
        product.setDescription(pro_des);
        product.setQuantity(pro_quant);
        product.setStatus(pro_status);
        product.setCategoryId(pro_cate);

        responseData.setData(productService.addProduct(file, product));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping(value = "/adda")
    public ResponseEntity<?> addProduct1(
                                        @RequestParam int id){
        ResponseData responseData = new ResponseData();
        System.out.println("hello add product");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}

