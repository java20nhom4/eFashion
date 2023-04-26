package com.cybersoft.eFashion.controller;

import com.cybersoft.eFashion.dto.EmailDTO;
import com.cybersoft.eFashion.dto.OrderItemsDTO;
import com.cybersoft.eFashion.dto.RatingDTO;
import com.cybersoft.eFashion.payload.ResponseData;
import com.cybersoft.eFashion.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping(value = "/rating")
public class RatingController {
    @Autowired
    RatingService ratingService;

    @GetMapping("")
    public ResponseEntity<?> getAllRating(){
        ResponseData responseData = new ResponseData();
        responseData.setData(ratingService.getAllRating());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> getRatingByProduct(@RequestParam int pro_id){
        ResponseData responseData = new ResponseData();
        responseData.setData(ratingService.getRatingByProduct(pro_id));
//        return new ResponseEntity<>(responseData, HttpStatus.OK);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<?> getRatingByUser(@RequestParam int user_id){
        ResponseData responseData = new ResponseData();
        responseData.setData(ratingService.getRatingByUser(user_id));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addRating(@RequestParam("file") MultipartFile file,
                                       @RequestParam int user_id,
                                       @RequestParam int product_id,
                                       @RequestParam int star,
                                       @RequestParam String comment){
        ResponseData responseData = new ResponseData();
        RatingDTO rate_dto = new RatingDTO();
        rate_dto.setUser_id(user_id);
        rate_dto.setPro_id(product_id);
        rate_dto.setStar(star);
        rate_dto.setComment(comment);
        if(user_id == -1) {
            responseData.setData(false);
            responseData.setStatusCode(400);
            responseData.setDesc("Ban chua dang nhap, rating failed");
        }else {
            responseData.setDesc("Rating succeeded!!");
            responseData.setData(ratingService.insertRating(file, rate_dto));
            responseData.setStatusCode(200);
        }
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }

    @PostMapping(value = "/addNoImage")
    public ResponseEntity<?> addRatingNoImage(
                                       @RequestParam int user_id,
                                       @RequestParam int product_id,
                                       @RequestParam int star,
                                       @RequestParam String comment){
        ResponseData responseData = new ResponseData();
        RatingDTO rate_dto = new RatingDTO();
        rate_dto.setUser_id(user_id);
        rate_dto.setPro_id(product_id);
        rate_dto.setStar(star);
        rate_dto.setComment(comment);
        if(user_id == -1) {
            responseData.setData(false);
            responseData.setStatusCode(400);
            responseData.setDesc("Ban chua dang nhap, rating failed");
        }else {
            MultipartFile file = null;
            responseData.setDesc("Rating succeeded!!");
            responseData.setData(ratingService.insertRating(file, rate_dto));
            responseData.setStatusCode(200);
        }
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?>removeRating(@RequestParam int rating_id){
        ResponseData responseData = new ResponseData();
        responseData.setData(ratingService.removeRating(rating_id));
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> updateRating(@RequestParam("file") MultipartFile file,
                                          @RequestParam int id,
                                          @RequestParam int user_id,
                                          @RequestParam int product_id,
                                          @RequestParam int star,
                                          @RequestParam String comment){
        ResponseData responseData = new ResponseData();
        RatingDTO rate_dto = new RatingDTO();
        rate_dto.setId(id);
        rate_dto.setUser_id(user_id);
        rate_dto.setPro_id(product_id);
        rate_dto.setStar(star);
        rate_dto.setComment(comment);
        responseData.setData(ratingService.insertRating(file, rate_dto));
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }
}
