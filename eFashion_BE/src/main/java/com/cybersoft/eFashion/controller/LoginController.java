package com.cybersoft.eFashion.controller;

import com.cybersoft.eFashion.dto.UserDTO;
import com.cybersoft.eFashion.payload.ResponseData;
import com.cybersoft.eFashion.service.imp.SignupServiceImp;
import com.cybersoft.eFashion.utils.JwtUtilsHelpers;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/***
 * NGƯỜI THỰC HIỆN: PHẠM NGỌC HÙNG
 * NGÀY: 31/03/2023
 *
 */

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtilsHelpers jwtUtilsHelpers;

    @Autowired
    SignupServiceImp signupServiceImp;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(
            @RequestParam String username,
            @RequestParam String password
    ) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        //Tạo chứng thực
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        //Parse về Json
        Gson gson = new Gson();
        String data = gson.toJson(authentication);

        System.out.println("Data: "+data);

        ResponseData responseData = new ResponseData();
        responseData.setData(jwtUtilsHelpers.generateToken(data));

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) {
        ResponseData responseData = new ResponseData();

        if(signupServiceImp.checkExistEmail(userDTO.getEmail()) == false) {
            if (signupServiceImp.signup(userDTO)) {
                responseData.setData(true);
                responseData.setStatusCode(200);
                responseData.setDesc("Đăng ký thành công");
            } else {
                responseData.setData(signupServiceImp.signup(userDTO));
                responseData.setStatusCode(400);
                responseData.setDesc("Đăng ký thất bại");
            }
        }else {
            responseData.setData(true);
            responseData.setDesc("Email đã tồn tại !");
            responseData.setStatusCode(400);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
