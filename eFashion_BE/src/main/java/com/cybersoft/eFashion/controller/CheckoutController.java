package com.cybersoft.eFashion.controller;

import com.cybersoft.eFashion.payload.ResponseData;
import com.cybersoft.eFashion.service.LoginService;
import com.cybersoft.eFashion.utils.JwtUtilsHelpers;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    JwtUtilsHelpers jwtUtilsHelpers;

    @Autowired
    LoginService loginService;
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
}
