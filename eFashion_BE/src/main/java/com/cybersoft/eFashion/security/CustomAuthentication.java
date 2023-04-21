package com.cybersoft.eFashion.security;

import com.cybersoft.eFashion.entity.Roles;
import com.cybersoft.eFashion.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthentication implements AuthenticationProvider {

    @Autowired
    LoginServiceImp loginServiceImp;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (loginServiceImp.login(username, password)) {
            System.out.println(username + " - " + password);
            return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        }else {
            return null;
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}