package com.cybersoft.eFashion.repository;

import com.cybersoft.eFashion.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    //Login
    List<Users> findByEmailAndPassword(String username, String password);
    //Check exist email
    List<Users> findByEmail(String email);
    Users getUsersById(int id);
}
