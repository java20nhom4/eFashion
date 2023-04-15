package com.cybersoft.eFashion.controller;

import com.cybersoft.eFashion.dto.OrderItemsDTO;
import com.cybersoft.eFashion.dto.PlaceOrderDTO;
import com.cybersoft.eFashion.dto.UserDTO;
import com.cybersoft.eFashion.payload.ResponseData;
import com.cybersoft.eFashion.service.CartService;
import com.cybersoft.eFashion.service.CheckoutService;
import com.cybersoft.eFashion.service.LoginService;
import com.cybersoft.eFashion.utils.JwtUtilsHelpers;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    JwtUtilsHelpers jwtUtilsHelpers;

    @Autowired
    LoginService loginService;

    @Autowired
    CartService cartService;

    @Autowired
    CheckoutService checkoutService;
    @GetMapping("")
    public ResponseEntity<?>getEmailFromToken(@RequestParam String token){
        ResponseData responseData = new ResponseData();
        responseData.setData(jwtUtilsHelpers.getDataFromToken(token));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email){
        ResponseData responseData = new ResponseData();

        responseData.setData(loginService.getUserByEmail(email));
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }

    @GetMapping("/getOderItems")
    public ResponseEntity<?> getOrderItemById(@RequestParam int id){
        ResponseData responseData = new ResponseData();

        responseData.setData(cartService.getOrderItemsById(id));
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addCart(
                                     @RequestParam int userId,
                                     @RequestParam int orderItemId
                                     ){
        PlaceOrderDTO placeOrderDTO = new PlaceOrderDTO();
        placeOrderDTO.setOrderItemId(orderItemId);
        placeOrderDTO.setUserId(userId);
        ResponseData responseData = new ResponseData();

        responseData.setData(checkoutService.addPlaceOrder(placeOrderDTO));
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(
            @RequestParam int id,
            @RequestParam String phone,
            @RequestParam String fullname,
            @RequestParam String address
    ){

        ResponseData responseData = new ResponseData();

        responseData.setData(checkoutService.updateUserById(id,phone,fullname,address));
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }
}
