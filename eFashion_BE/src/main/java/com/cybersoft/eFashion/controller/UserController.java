package com.cybersoft.eFashion.controller;

import com.cybersoft.eFashion.dto.EmailDTO;
import com.cybersoft.eFashion.dto.UserDTO;
import com.cybersoft.eFashion.payload.ResponseData;
import com.cybersoft.eFashion.service.imp.EmailServiceImp;
import com.cybersoft.eFashion.service.imp.UsersServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UsersServiceImp usersServiceImp;

    @Autowired
    private EmailServiceImp emailServiceImp;

    private UserDTO checkUserDTO = new UserDTO();
    private String OTP = null;
    private String email = null;

    // Send OTP
    @PostMapping("/sendEmailConfirmOTP")
    public ResponseEntity<?> sendMail(@RequestParam String email,
                                      @RequestParam String password,
                                      @RequestParam String fullName,
                                      @RequestParam String phone,
                                      @RequestParam String address,
                                      @RequestParam MultipartFile avatar) {

        ResponseData responseData = new ResponseData();
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        userDTO.setFullName(fullName);
        userDTO.setPhone(phone);
        userDTO.setAddress(address);
        userDTO.setAvatar(avatar.getName());
        userDTO.setRoleId(3);

        if(usersServiceImp.checkExistEmail(userDTO.getEmail()) == false) {

            EmailDTO emailDTO = supportEmail(userDTO.getEmail());

            // Set responseData
            responseData.setData(emailServiceImp.sendSimpleMail(emailDTO));
            responseData.setDesc("Gửi thành công OTP cho user " + emailDTO.getRecipient() + " " + OTP);
            responseData.setStatusCode(200);

            // Set UserDTO
            checkUserDTO = userDTO;
        }else {
            responseData.setData(false);
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
        System.out.println(inputOTP);

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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int idUser) {

        boolean isSuccess = usersServiceImp.deleteUser(idUser);

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

    // OTP of forgetting password
    @PostMapping("/findPasswordByEmail")
    public ResponseEntity<?> findPasswordByEmail(@RequestParam String inputEmail) {

        ResponseData responseData = new ResponseData();
        System.out.println(inputEmail);
        if(usersServiceImp.checkExistEmail(inputEmail)) {
            EmailDTO emailDTO = supportEmail(inputEmail);
            email = inputEmail;
            responseData.setData(emailServiceImp.sendSimpleMail(emailDTO)+OTP);
            responseData.setStatusCode(200);
            responseData.setDesc("Gui thanh cong OTP");
        }else {
            responseData.setDesc("Email khong ton tai");
            responseData.setData(false);
            responseData.setStatusCode(400);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/checkOTPOfForgettingPassword")
    public ResponseEntity<?> checkOTPOfForgettingPassword(@RequestParam String inputOTP,
                                                          @RequestParam String newPassword) {

        ResponseData responseData = new ResponseData();
        System.out.println(inputOTP+newPassword);
        if(inputOTP.equals(OTP)) {
            UserDTO userDTO = usersServiceImp.getUserByEmail(email);
            System.out.println(userDTO.getEmail()+" "+userDTO.getPhone()+" "+userDTO.getPassword());
            userDTO.setPassword(newPassword);
            if(usersServiceImp.updateUser(userDTO, null)) {
                responseData.setData(true);
                responseData.setDesc("Thay doi mat khau thanh cong");
                responseData.setStatusCode(200);
            }else {
                responseData.setData(false);
                responseData.setDesc("Thay doi mat khau khong thanh cong");
                responseData.setStatusCode(400);
            }
        }else {
            responseData.setData(false);
            responseData.setDesc("Sai OTP");
            responseData.setStatusCode(400);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    //  Support email
    private EmailDTO supportEmail(String email) {

        // Create OTP
        Random random = new Random();
        OTP = Integer.toString(
                random.ints(100000, 999999)
                        .findFirst()
                        .getAsInt());

        // Set emailDTO
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setMsgBody("Your OTP is " + OTP);
        emailDTO.setRecipient(email);
        emailDTO.setSubject("[eFashion OTP]");

        return emailDTO;
    }

}
