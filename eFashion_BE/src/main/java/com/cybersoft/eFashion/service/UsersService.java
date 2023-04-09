package com.cybersoft.eFashion.service;

import com.cybersoft.eFashion.dto.UserDTO;
import com.cybersoft.eFashion.entity.Roles;
import com.cybersoft.eFashion.entity.Users;
import com.cybersoft.eFashion.repository.UserRepository;
import com.cybersoft.eFashion.service.imp.UsersServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService implements UsersServiceImp {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileStorageService fileStorageService;

    //sign up
    @Override
    public boolean signup(UserDTO userDTO) {

        try {
            Roles roles = new Roles();
            roles.setId(userDTO.getRoleId());
            Users users = new Users();
            users.setEmail(userDTO.getEmail());
            users.setPassword(userDTO.getPassword());
            users.setPhone(userDTO.getPhone());
            users.setFullname(userDTO.getFullName());
            users.setAvatar(userDTO.getAvatar());
            users.setAddress(userDTO.getAddress());
            users.setRoles(roles);

            //save user
            userRepository.save(users);

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean checkExistEmail(String email) {
        return userRepository.findByEmail(email).size()>0;
    }

    //delete user
    @Override
    public boolean deleteUser(UserDTO userDTO) {

        //Delete image of user by email user
        boolean isSuccess = fileStorageService.removeFile(
                userRepository.findByEmail(userDTO.getEmail()).get(0).getAvatar(), FolderType.Users);

        if(isSuccess) {
            try {
                //delete user by id user
                userRepository.deleteById(userRepository.findByEmail(userDTO.getEmail()).get(0).getId());

                return true;
            }catch (Exception e) {
                System.out.println("Error deleteUser");

                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public boolean updateUser(UserDTO userDTO, MultipartFile file) {
        Users users = userRepository.findByEmail(userDTO.getEmail()).get(0);

        //save new image if exist
        if(userDTO.getAvatar() != null) {
            if (!userDTO.getAvatar().equals("")) {
                fileStorageService.saveFiles(file, file.getOriginalFilename(), FolderType.Users);

                //delete old image if exist
                if (users.getAvatar() != null) {
                    if (!users.getAvatar().equals("")) {
                        fileStorageService.removeFile(users.getAvatar(), FolderType.Users);
                    }
                }

                //update name avatar
                users.setAvatar(userDTO.getAvatar());
            } else {
                users.setAddress(users.getAddress());
            }
        }

        try {
            users.setPhone(userDTO.getPhone());
            users.setFullname(userDTO.getFullName());
            users.setAddress(userDTO.getAddress());

            userRepository.save(users);

            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {

        //Get all users
        List<Users> list = userRepository.findAll();
        List<UserDTO> dtoList = new ArrayList<>();

        for (Users users : list) {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(users.getEmail());
            userDTO.setFullName(users.getFullname());
            userDTO.setAvatar(users.getAvatar());
            userDTO.setPhone(users.getPhone());
            userDTO.setAddress(users.getAddress());
            userDTO.setRoleId(users.getRoles().getId());

            dtoList.add(userDTO);
        }

        return dtoList;
    }

    @Override
    public UserDTO getUserByEmail(UserDTO userDTO) {
        Users user = userRepository.findByEmail(userDTO.getEmail()).get(0);
        userDTO.setFullName(user.getFullname());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setPhone(user.getPhone());
        userDTO.setAddress(user.getAddress());
        userDTO.setRoleId(user.getRoles().getId());

        return userDTO;
    }
}