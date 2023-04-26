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
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
@Service
public class RatingService implements RatingServiceImp {
    @Value("${fileStorage.path}")
    private String parentFolder;

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
        String path  = parentFolder + "\\" + FolderType.Ratings.toString() + "\\" + rate.getImage();
        System.out.println(path);
        rate_dto.setImage(path);
        rate_dto.setUser_name(rate.getUsers().getFullname());
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
        int idImage = ratingRepository.getMaxId() + 1;
        String newFileName = "";
        // Save Image First
        if (file != null){
            String now = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            newFileName = idImage + "_" + ratingDTO.getUser_id() + "_" + ratingDTO.getPro_id() + "_" + now  + "." + extension;
            isSaveFileSuccess = fileStorageServiceImp.saveFiles(file, newFileName, FolderType.Ratings);
        }
        // Insert then
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
                if (file != null){
                    rating.setImage(newFileName);
                }
                ratingRepository.save(rating);
                isInsertSuccess = true;
            }
        }
        System.out.println("Add: " + isInsertSuccess);
        return isInsertSuccess;
    }

    @Override
    public boolean removeRating(int rating_id) {
        Boolean isRemoveSuccess = true;
        Boolean isRemoveImage = true;
        Boolean isRemoveDataSuccess = true;
        RatingProducts rating = ratingRepository.getById(rating_id);
        try {
            ratingRepository.deleteById(rating_id);
        }
        catch (Exception e){
            isRemoveDataSuccess = false;
            isRemoveSuccess = false;
        }
        if (isRemoveDataSuccess){ // Remove image in forder
            String nameImage = rating.getImage();
            if (nameImage != null && !nameImage.isEmpty()){
                isRemoveImage = fileStorageServiceImp.removeFile(nameImage, FolderType.Ratings);
            }
        }
        if (isRemoveDataSuccess && !isRemoveSuccess){
            System.out.println("Delete data OKAY - Delete image FAIL");
            isRemoveImage = false;
        }
        return isRemoveImage;
    }

    @Override
    public boolean updateRating(MultipartFile file, RatingDTO ratingDTO) {
        boolean isUpdateSuccess = false;
        RatingProducts rating = ratingRepository.getById(ratingDTO.getId());
        if (rating != null ){
            boolean isSaveFileSuccess = true;
            String newFileName = "";
            if (file != null){
                String now = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                newFileName = ratingDTO.getId() + "_" + ratingDTO.getUser_id() + "_" + ratingDTO.getPro_id() + "_" + now  + "." + extension;
                isSaveFileSuccess = fileStorageServiceImp.saveFiles(file, newFileName, FolderType.Ratings);
            }
            if (isSaveFileSuccess){
                Users user = userRepository.getUsersById(ratingDTO.getUser_id());
                Products product = productsRepository.getProductsById(ratingDTO.getPro_id());
                if (user == null || product == null){
                    isUpdateSuccess = false;
                }
                else{
                    rating.setUsers(user);
                    rating.setProducts(product);
                    rating.setStar(ratingDTO.getStar());
                    rating.setComment(ratingDTO.getComment());
                    rating.setImage(newFileName);
                    ratingRepository.save(rating);
                    isUpdateSuccess = true;
                }
            }
        }
        return isUpdateSuccess;
    }
}
