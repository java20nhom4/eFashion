package com.cybersoft.eFashion.service.imp;

import com.cybersoft.eFashion.dto.OrderItemsDTO;
import com.cybersoft.eFashion.dto.UserDTO;
import com.cybersoft.eFashion.entity.Users;

import java.util.List;

public interface LoginServiceImp {
    List<UserDTO> getUserByEmail(String email);
    boolean login(String username, String password);
    int getIdRole(String username);
}