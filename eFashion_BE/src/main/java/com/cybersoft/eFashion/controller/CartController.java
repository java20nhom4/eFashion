package com.cybersoft.eFashion.controller;

import com.cybersoft.eFashion.dto.OrderItemsDTO;
import com.cybersoft.eFashion.payload.ResponseData;
import com.cybersoft.eFashion.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @GetMapping()
    public ResponseEntity<?> getOrderItemByUid(@RequestParam int userId){
        ResponseData responseData = new ResponseData();
        responseData.setData(cartService.getOrderItemByUid(userId));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addCart( @RequestParam int proId, @RequestParam int userId){

        ResponseData responseData = new ResponseData();
        boolean isExists = cartService.findProductById(proId,userId);

        System.out.println(isExists);

        if(isExists){
            cartService.plusQuantity(proId,userId);
        }else {
            responseData.setData(cartService.addCart(proId,userId));
        }
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }

    @PutMapping("/plusQuantity")
    public ResponseEntity<?> plusQuantity(@RequestParam int proId, @RequestParam int userId){
        cartService.plusQuantity(proId,userId);
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    @PutMapping("/subtractQuantity")
    public ResponseEntity<?> subtractQuantity(@RequestParam int proId, @RequestParam int  userId){
        if(cartService.getQuantityById(proId,userId)>1){
            cartService.subtractQuantity(proId,userId);
        }
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    @DeleteMapping("/removeProduct")
    public ResponseEntity<?>removeProduct(@RequestParam int proId, @RequestParam int  userId){
        ResponseData responseData = new ResponseData();
        responseData.setData(cartService.deleteProductById(proId,userId));
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }
}
