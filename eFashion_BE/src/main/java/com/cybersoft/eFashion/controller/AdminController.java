package com.cybersoft.eFashion.controller;

import com.cybersoft.eFashion.payload.ResponseData;
import com.cybersoft.eFashion.service.imp.AdminServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    int id = 0;

    @Autowired
    private AdminServiceImp adminServiceImp;

    @PostMapping("/postIdAdmin")
    private ResponseEntity<?> postIdAdmin(@RequestParam int idAdmin) {
        ResponseData responseData = new ResponseData();
        id = idAdmin;
        if (idAdmin != 0) {
            responseData.setData(idAdmin);
            responseData.setStatusCode(200);
            responseData.setDesc("Lấy thành công id admin");
        } else {
            responseData.setData(0);
            responseData.setStatusCode(400);
            responseData.setDesc("Lấy thất bại id admin");
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> admin() {
        ResponseData responseData = new ResponseData();
        if (id != 0) {
            responseData.setData(adminServiceImp.getInforAdminPage(1));
            responseData.setStatusCode(200);
            responseData.setDesc("Lấy thành công thông tin trang admin");
        } else {
            responseData.setData(0);
            responseData.setStatusCode(400);
            responseData.setDesc("Lấy thất bại thông tin trang admin");
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
