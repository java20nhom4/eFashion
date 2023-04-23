package com.cybersoft.eFashion.service.imp;

import com.cybersoft.eFashion.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UsersServiceImp {

    //sign up
    boolean signup(UserDTO userDTO);

    //Check exist email
    boolean checkExistEmail(String email);

    //Delete user
    boolean deleteUser(int id);

    //Update user
    boolean updateUser(UserDTO userDTO, MultipartFile file);

    // Add user
    boolean addUser(UserDTO userDTO, MultipartFile file);

    //Get all users
    List<UserDTO> getAllUsers();

    //Get users by id
    UserDTO getUserByEmail(String email);

}
