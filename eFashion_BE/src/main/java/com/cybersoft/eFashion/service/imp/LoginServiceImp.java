package com.cybersoft.eFashion.service.imp;

import com.cybersoft.eFashion.dto.UserDTO;

public interface LoginServiceImp {

    boolean login(String username, String password);
}