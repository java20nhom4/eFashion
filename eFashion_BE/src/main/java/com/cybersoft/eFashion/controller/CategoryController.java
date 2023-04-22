package com.cybersoft.eFashion.controller;


import com.cybersoft.eFashion.dto.CategoryDTO;
import com.cybersoft.eFashion.payload.ResponseData;
import com.cybersoft.eFashion.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    long idDetail = 0;

    @Autowired
    CategoryServiceImp categoryServiceImp;

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO) {

//    public ResponseEntity<?> addCategory(@RequestParam String name, @RequestParam String description) {
//        CategoryDTO categoryDTO = new CategoryDTO();
//        categoryDTO.setName(name);
//        categoryDTO.setDescription(description);

        boolean isSuccess = categoryServiceImp.addCatergory(categoryDTO);
        ResponseData responseData = new ResponseData();
        if (isSuccess) {
            responseData.setData(true);
            responseData.setDesc("Them thanh cong category");
            responseData.setStatusCode(200);
        } else {
            responseData.setData(false);
            responseData.setDesc("Them that bai category");
            responseData.setStatusCode(400);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCategory(@RequestParam long id) {

        boolean isSuccess = categoryServiceImp.deleteCatergory(id);
        ResponseData responseData = new ResponseData();
        if (isSuccess) {
            responseData.setData(true);
            responseData.setDesc("Xóa thành công category ");
            responseData.setStatusCode(200);
        } else {
            responseData.setData(false);
            responseData.setDesc("Xóa thất bại category ");
            responseData.setStatusCode(400);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        boolean isSuccess = categoryServiceImp.updateCategory(categoryDTO);
        ResponseData responseData = new ResponseData();
        if (isSuccess) {
            responseData.setData(true);
            responseData.setDesc("Sửa thành công");
            responseData.setStatusCode(200);
        } else {
            responseData.setData(false);
            responseData.setDesc("Sửa thất bại");
            responseData.setStatusCode(400);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCategory() {
        ResponseData responseData = new ResponseData();
        if (categoryServiceImp.getAllCategory().size() > 0) {
            responseData.setData(categoryServiceImp.getAllCategory());
            responseData.setDesc("Lấy thành công ");
            responseData.setStatusCode(200);
        } else {
            responseData.setData(false);
            responseData.setData("Lấy thông tin thất bại");
            responseData.setStatusCode(400);

        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Get category by id
    @GetMapping("/getCategoryById")
    public ResponseEntity<?> getCategoryByid() {
        ResponseData responseData = new ResponseData();
        responseData.setData(categoryServiceImp.getCategoryById(idDetail));
        responseData.setDesc("Lấy thành công category");
        responseData.setStatusCode(200);

        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }

    // Get id detail
    @PostMapping("/getIdDetail")
    public ResponseEntity<?> postCategory(@RequestParam long id) {
        ResponseData responseData = new ResponseData();
        idDetail = id;
        if (idDetail != 0) {
            responseData.setData(idDetail);
            responseData.setDesc("Lấy thành công id detail");
            responseData.setStatusCode(200);
        } else {
            responseData.setData(idDetail);
            responseData.setDesc("Lấy thất bại id detail");
            responseData.setStatusCode(400);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);

    }

}
