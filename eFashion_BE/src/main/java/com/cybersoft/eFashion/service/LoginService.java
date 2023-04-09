package com.cybersoft.eFashion.service;

import com.cybersoft.eFashion.dto.UserDTO;
import com.cybersoft.eFashion.repository.UserRepository;
import com.cybersoft.eFashion.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginServiceImp {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean login(String username, String password) {
        return userRepository.findByEmailAndPassword(username, password).size() > 0;
    }
}
