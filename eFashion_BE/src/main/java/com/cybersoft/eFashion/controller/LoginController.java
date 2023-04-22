package com.cybersoft.eFashion.controller;


import com.cybersoft.eFashion.dto.TokenDTO;

import com.cybersoft.eFashion.payload.ResponseData;
import com.cybersoft.eFashion.repository.UserRepository;
import com.cybersoft.eFashion.service.imp.LoginServiceImp;
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


//@CrossOrigin
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    LoginServiceImp loginServiceImp;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtilsHelpers jwtUtilsHelpers;
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

        ResponseData responseData = new ResponseData();
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(jwtUtilsHelpers.generateToken(data, loginServiceImp.getIdRole(username)));
        responseData.setData(tokenDTO.getToken());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
