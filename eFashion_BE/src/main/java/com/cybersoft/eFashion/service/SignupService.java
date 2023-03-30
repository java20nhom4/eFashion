package com.cybersoft.eFashion.service;

import com.cybersoft.eFashion.dto.UserDTO;
import com.cybersoft.eFashion.entity.Roles;
import com.cybersoft.eFashion.entity.Users;
import com.cybersoft.eFashion.repository.UserRepository;
import com.cybersoft.eFashion.service.imp.SignupServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService implements SignupServiceImp {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean signup(UserDTO userDTO) {
        Roles roles = new Roles();
        roles.setId(userDTO.getRoleId());

        Users users = new Users();
        users.setEmail(userDTO.getEmail());
        users.setPassword(userDTO.getPassword());
        users.setPhone(userDTO.getPhone());
        users.setFullname(userDTO.getFullName());
        users.setAvatar(userDTO.getAvatar());
        users.setRoles(roles);

        try {
            userRepository.save(users);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean checkExistEmail(String email) {

        return userRepository.findByEmail(email).size()>0;

    }
}
