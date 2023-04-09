package com.cybersoft.eFashion.controller;

import com.cybersoft.eFashion.dto.EmailDTO;
import com.cybersoft.eFashion.dto.UserDTO;
import com.cybersoft.eFashion.payload.ResponseData;
import com.cybersoft.eFashion.service.imp.EmailServiceImp;
import com.cybersoft.eFashion.service.imp.UsersServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UsersServiceImp usersServiceImp;

    @Autowired
    private EmailServiceImp emailServiceImp;

    private UserDTO checkUserDTO = new UserDTO();
    private String OTP = null;

    // Send OTP
    @PostMapping("/sendEmailConfirmOTP")
    public ResponseEntity<?> sendMail(@RequestBody UserDTO userDTO) {

        ResponseData responseData = new ResponseData();
        if(usersServiceImp.checkExistEmail(userDTO.getEmail()) == false) {

            // Create OTP
            Random random = new Random();
            OTP = Integer.toString(
                    random.ints(100000, 999999)
                            .findFirst()
                            .getAsInt());

            // Set emailDTO
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setMsgBody("Your OTP is " + OTP);
            emailDTO.setRecipient(userDTO.getEmail());
            emailDTO.setSubject("[eFashion OTP]");

            // Set responseData
            responseData.setData(emailServiceImp.sendSimpleMail(emailDTO));
            responseData.setDesc("Gửi thành công OTP cho user " + emailDTO.getRecipient() + " " + OTP);
            responseData.setStatusCode(200);

            // Set UserDTO
            checkUserDTO = userDTO;
        }else {
            responseData.setData("Gửi thất bại OTP");
            responseData.setDesc("Email đã tồn tại !");
            responseData.setStatusCode(400);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Sign up
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestParam String inputOTP) {

        //Set responseData
        ResponseData responseData = new ResponseData();

        if(inputOTP.equals(OTP)) {
            if (usersServiceImp.signup(checkUserDTO)) {
                responseData.setData(true);
                responseData.setStatusCode(200);
                responseData.setDesc("Đăng ký thành công");

                checkUserDTO = null;
                OTP = null;
            } else {
                responseData.setData(false);
                responseData.setStatusCode(400);
                responseData.setDesc("Đăng ký thất bại");
            }
        }else {
            responseData.setData(OTP);
            responseData.setDesc("Sai OTP");
            responseData.setData(400);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Delete user
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO userDTO) {

        boolean isSuccess = usersServiceImp.deleteUser(userDTO);

        //Set responseData
        ResponseData responseData = new ResponseData();
        if (isSuccess) {
            responseData.setData(true);
            responseData.setDesc("Xóa user thành công");
            responseData.setStatusCode(200);
        }else {
            responseData.setData(false);
            responseData.setDesc("Xóa user thất bại");
            responseData.setStatusCode(400);
        }

        return new ResponseEntity(responseData, HttpStatus.OK);
    }

    // Update user
    @PutMapping("/update")
    private ResponseEntity<?> update(@RequestParam String email,
                                     @RequestParam String phone,
                                     @RequestParam String fullName,
                                     @RequestParam String address,
                                     @RequestParam MultipartFile file) {

            //Set DTO
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(email);
            userDTO.setPhone(phone);
            userDTO.setFullName(fullName);
            userDTO.setAddress(address);
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

    // Get all users
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {

        //Set responseData
        ResponseData responseData = new ResponseData();
        if(usersServiceImp.getAllUsers().size()>0) {
            responseData.setData(usersServiceImp.getAllUsers());
            responseData.setDesc("Lấy thành công danh sách user");
            responseData.setStatusCode(200);
        }else {
            responseData.setData(false);
            responseData.setDesc("Lấy thất bại danh sách user");
            responseData.setStatusCode(400);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Get user by email
    @GetMapping("/getUserByEmail")
    public ResponseEntity<?> getUserById(@RequestBody UserDTO userDTO) {

        ResponseData responseData = new ResponseData();
        if(usersServiceImp.getUserByEmail(userDTO).getFullName() != null) {
            responseData.setData(usersServiceImp.getUserByEmail(userDTO));
            responseData.setDesc("Lấy thành công user");
            responseData.setStatusCode(200);
        }else {
            responseData.setData(false);
            responseData.setDesc("Lấy thất bại user");
            responseData.setStatusCode(400);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
