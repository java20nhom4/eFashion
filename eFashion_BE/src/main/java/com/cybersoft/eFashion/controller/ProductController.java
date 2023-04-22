package com.cybersoft.eFashion.controller;

import com.cybersoft.eFashion.dto.ProductsDto;
import com.cybersoft.eFashion.entity.Products;
import com.cybersoft.eFashion.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

