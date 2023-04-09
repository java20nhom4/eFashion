package com.cybersoft.eFashion.service.imp;

import com.cybersoft.eFashion.dto.RatingDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RatingServiceImp {
    List<RatingDTO> getAllRating();
    List<RatingDTO> getRatingByProduct(int pro_id);
    List<RatingDTO> getRatingByUser(int user_id);
    boolean insertRating(MultipartFile file, RatingDTO ratingDTO);
    boolean removeRating(int rating_id);
    boolean updateRating(MultipartFile file, RatingDTO ratingDTO);
}
