package com.cybersoft.eFashion.service.imp;

import com.cybersoft.eFashion.dto.UserDTO;

public interface SignupServiceImp {

    boolean signup(UserDTO userDTO);
    boolean checkExistEmail(String email);

}
