package com.cybersoft.eFashion.service;

import com.cybersoft.eFashion.dto.RatingDTO;
import com.cybersoft.eFashion.entity.Products;
import com.cybersoft.eFashion.entity.RatingProducts;
import com.cybersoft.eFashion.entity.Users;
import com.cybersoft.eFashion.repository.ProductsRepository;
import com.cybersoft.eFashion.repository.RatingRepository;
import com.cybersoft.eFashion.repository.UserRepository;
import com.cybersoft.eFashion.service.imp.FileStorageServiceImp;
import com.cybersoft.eFashion.service.imp.RatingServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
@Service
public class RatingService implements RatingServiceImp {
    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    FileStorageServiceImp fileStorageServiceImp;

    public RatingDTO ratingtoRatingDTO(RatingProducts rate){
        RatingDTO rate_dto = new RatingDTO();
        rate_dto.setId(rate.getId());
        rate_dto.setUser_id(rate.getUsers().getId());
        rate_dto.setPro_id(rate.getProducts().getId());
        rate_dto.setStar(rate.getStar());
        rate_dto.setComment(rate.getComment());
        rate_dto.setImage(rate.getImage());
        return rate_dto;
    }

    @Override
    public List<RatingDTO> getAllRating() {
        List<RatingDTO> list_rating_dto = new ArrayList<>();
        List<RatingProducts> list_rating_products;
        list_rating_products = ratingRepository.findAll();
        for (RatingProducts rate: list_rating_products) {
            RatingDTO rate_dto = ratingtoRatingDTO(rate);
            list_rating_dto.add(rate_dto);
        }
        return list_rating_dto;
    }

    @Override
    public List<RatingDTO> getRatingByProduct(int pro_id) {
        List<RatingDTO> list_rating_dto = new ArrayList<>();
        List<RatingProducts> list_rating_products;
        list_rating_products = ratingRepository.findByProducts_Id(pro_id);
        for (RatingProducts rate: list_rating_products) {
            RatingDTO rate_dto = ratingtoRatingDTO(rate);
            list_rating_dto.add(rate_dto);
        }
        return list_rating_dto;
    }

    @Override
    public List<RatingDTO> getRatingByUser(int user_id) {
        List<RatingDTO> list_rating_dto = new ArrayList<>();
        List<RatingProducts> list_rating_products;
        list_rating_products = ratingRepository.findByUsers_Id(user_id);
        for (RatingProducts rate : list_rating_products) {
            RatingDTO rate_dto = ratingtoRatingDTO(rate);
            list_rating_dto.add(rate_dto);
        }
        return list_rating_dto;
    }

    @Override
    public boolean insertRating(MultipartFile file, RatingDTO ratingDTO) {
        boolean isInsertSuccess = false;
        boolean isSaveFileSuccess = true;
        String newFileName = "";
        if (file != null){
            String now = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            newFileName = ratingDTO.getId() + "_" + ratingDTO.getUser_id() + "_" + ratingDTO.getPro_id() + "_" + now;
            isSaveFileSuccess = fileStorageServiceImp.saveFiles(file, "Hello", FolderType.Ratings);
        }
        if (isSaveFileSuccess){
            Users user = userRepository.getUsersById(ratingDTO.getUser_id());
            Products product = productsRepository.getProductsById(ratingDTO.getPro_id());
            if (user == null || product == null){
                isInsertSuccess = false;
            }
            else{
                RatingProducts rating = new RatingProducts();
                rating.setUsers(user);
                rating.setProducts(product);
                rating.setStar(ratingDTO.getStar());
                rating.setComment(ratingDTO.getComment());
                rating.setImage(newFileName);
                isInsertSuccess = true ;
            }
        }
        System.out.println("ADd thanh cong");
        return isInsertSuccess;
    }
}
