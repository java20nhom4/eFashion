package com.cybersoft.eFashion.service;
;
import com.cybersoft.eFashion.dto.OrderItemsDTO;
import com.cybersoft.eFashion.dto.UserDTO;
import com.cybersoft.eFashion.entity.OrderItems;
import com.cybersoft.eFashion.entity.Users;
import com.cybersoft.eFashion.repository.UserRepository;
import com.cybersoft.eFashion.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements LoginServiceImp {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDTO> getUserByEmail(String email) {
        List<UserDTO> list= new ArrayList<>();
        for ( Users users :userRepository.findByEmail(email)){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(users.getId());
            userDTO.setPhone(users.getPhone());
            userDTO.setFullName(users.getFullname());
            userDTO.setEmail(users.getEmail());
            userDTO.setAddress(users.getAddress());
            userDTO.setRoleId(users.getRoles().getId());

            list.add(userDTO);
        }
        return list;
    }

    @Override
    public boolean login(String username, String password) {
        return userRepository.findByEmailAndPassword(username, password).size() > 0;
    }

    @Override
    public int getIdRole(String username) {
        return userRepository.findByEmail(username).get(0).getRoles().getId();
    }
}
