package com.cybersoft.eFashion.controller;

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
@RequestMapping(value = "/rating", consumes = {"multipart/form-data"})
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
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<?> getRatingByUser(@RequestParam int user_id){
        ResponseData responseData = new ResponseData();
        responseData.setData(ratingService.getRatingByUser(user_id));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addRating(@RequestParam("file") MultipartFile file, @RequestBody RatingDTO ratingDTO){
        System.out.println("asdfsad");
        ResponseData responseData = new ResponseData();
        responseData.setData(ratingService.insertRating(file, ratingDTO));
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }

}
