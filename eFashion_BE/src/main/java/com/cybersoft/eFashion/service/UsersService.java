package com.cybersoft.eFashion.service;

import com.cybersoft.eFashion.dto.UserDTO;
import com.cybersoft.eFashion.entity.Roles;
import com.cybersoft.eFashion.entity.Users;
import com.cybersoft.eFashion.repository.UserRepository;
import com.cybersoft.eFashion.service.imp.UsersServiceImp;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    public boolean deleteUser(int id) {

        //Delete image of user by email user
        boolean isSuccess = fileStorageService.removeFile(
                userRepository.findById(id).get(0).getAvatar(), FolderType.Users);

        if(true) {
            try {
                //delete user by id user
                userRepository.deleteById(id);

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
            }
        }

        try {
            users.setAddress(userDTO.getAddress());
            users.setPhone(userDTO.getPhone());
            users.setFullname(userDTO.getFullName());
            users.setPassword(userDTO.getPassword());
            Roles roles = new Roles();
            roles.setId(userDTO.getRoleId());
            users.setRoles(roles);

            userRepository.save(users);

            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addUser(UserDTO userDTO, MultipartFile file) {

        try {
            boolean isSaveFileSuccess = true;
            int idImage = userRepository.getMaxId() + 1;
            String newFileName = "";
            // Save Image First
            if (file != null){
                String now = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                newFileName = idImage + "_" + userDTO.getEmail() + "_" + now  + "." + extension;
                isSaveFileSuccess = fileStorageService.saveFiles(file, newFileName, FolderType.Users);
            }
            // Insert then
            if (isSaveFileSuccess){
                Users users = new Users();
                users.setEmail(userDTO.getEmail());
                users.setAddress(userDTO.getAddress());
                users.setPhone(userDTO.getPhone());
                users.setFullname(userDTO.getFullName());
                users.setPassword(userDTO.getPassword());
                users.setAvatar(newFileName);

                Roles roles = new Roles();
                roles.setId(userDTO.getRoleId());
                users.setRoles(roles);
                userRepository.save(users);
                return true;
            }
            return false;
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
    public UserDTO getUserByEmail(String email) {
        UserDTO userDTO = new UserDTO();
        Users user = userRepository.findByEmail(email).get(0);
        userDTO.setId(user.getId());
        userDTO.setEmail(email);
        userDTO.setFullName(user.getFullname());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setPhone(user.getPhone());
        userDTO.setAddress(user.getAddress());
        userDTO.setRoleId(user.getRoles().getId());
        userDTO.setPassword(user.getPassword());

        return userDTO;
    }
}
