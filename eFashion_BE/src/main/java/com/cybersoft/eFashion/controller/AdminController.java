package com.cybersoft.eFashion.controller;

import com.cybersoft.eFashion.dto.AdminDTO;
import com.cybersoft.eFashion.dto.UserDTO;
import com.cybersoft.eFashion.payload.ResponseData;
import com.cybersoft.eFashion.service.imp.AdminServiceImp;
import com.cybersoft.eFashion.service.imp.UsersServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    int idAmin = 0;
    String emailUserDetail = null;

    @Autowired
    private UsersServiceImp usersServiceImp;

    @Autowired
    private AdminServiceImp adminServiceImp;

    @PostMapping("/postIdAdmin")
    public ResponseEntity<?> postEmailAdmin(@RequestParam int id) {
        idAmin = id;

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    // Get user by email
    @GetMapping("/getUserByEmail")
    public ResponseEntity<?> getUserByEmail() {

        List<Object> list = new ArrayList<>();
        ResponseData responseData = new ResponseData();

        // Set data admin
        list.add(admin());

        if (emailUserDetail != null) {
            responseData.setData(usersServiceImp.getUserByEmail(emailUserDetail));
            responseData.setDesc("Lấy thành công user");
            responseData.setStatusCode(200);
        }else {
            responseData.setData(false);
            responseData.setDesc("Lấy thất bại user");
            responseData.setStatusCode(400);
        }

        // Set data user
        list.add(responseData);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/add")
    private ResponseEntity<?> add(@RequestParam String email,
                                     @RequestParam String phone,
                                     @RequestParam String fullName,
                                     @RequestParam String address,
                                     @RequestParam String password,
                                     @RequestParam int roleId,
                                     @RequestParam MultipartFile file) {

        //Set DTO
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setPhone(phone);
        userDTO.setFullName(fullName);
        userDTO.setAddress(address);
        userDTO.setPassword(password);
        userDTO.setRoleId(roleId);
        userDTO.setAvatar(file.getOriginalFilename());

        //Set responseData
        ResponseData responseData = new ResponseData();
        if(usersServiceImp.addUser(userDTO, file)) {
            responseData.setData(true);
            responseData.setStatusCode(200);
            responseData.setDesc("Thêm thành công");
        } else {
            responseData.setData(false);
            responseData.setStatusCode(400);
            responseData.setDesc("Thêm thất bại");
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Update user
    @PutMapping("/update")
    private ResponseEntity<?> update(@RequestParam String email,
                                     @RequestParam String phone,
                                     @RequestParam String fullName,
                                     @RequestParam String address,
                                     @RequestParam String password,
                                     @RequestParam int roleId,
                                     @RequestParam MultipartFile file) {

        //Set DTO
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setPhone(phone);
        userDTO.setFullName(fullName);
        userDTO.setAddress(address);
        userDTO.setPassword(password);
        userDTO.setRoleId(roleId);
        userDTO.setAvatar(file.getOriginalFilename());

        //Set responseData
        ResponseData responseData = new ResponseData();
        if(usersServiceImp.updateUser(userDTO, file)) {
            responseData.setData(true);
            responseData.setStatusCode(200);
            responseData.setDesc("Cập nhật thành công");
        } else {
            responseData.setData(false);
            responseData.setStatusCode(400);
            responseData.setDesc("Cập nhật thất bại");
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/postIdUser")
    private ResponseEntity<?> postIdUser(@RequestParam String emailUser) {
        ResponseData responseData = new ResponseData();
        emailUserDetail = emailUser;
        System.out.println(emailUserDetail);

        if (emailUserDetail != null) {
            responseData.setData(emailUserDetail);
            responseData.setStatusCode(200);
            responseData.setDesc("Lấy thành công id user");
        } else {
            responseData.setData(emailUserDetail);
            responseData.setStatusCode(400);
            responseData.setDesc("Lấy thất bại id user");
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> admin() {
        ResponseData responseData = new ResponseData();

        responseData.setData(adminServiceImp.getInforAdminPage(idAmin));
        responseData.setStatusCode(200);
        responseData.setDesc("Lấy thành công thông tin trang admin");

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
