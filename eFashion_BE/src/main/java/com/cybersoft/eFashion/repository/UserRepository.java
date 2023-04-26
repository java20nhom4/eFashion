package com.cybersoft.eFashion.repository;

import com.cybersoft.eFashion.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    List<Users> findByEmailAndPassword(String username, String password);
    //Check exist email
    List<Users> findByEmail(String email);
    List<Users> findById(int id);
    Users getUsersById(int id);
    List<Users> findAll();
    @Query("SELECT coalesce(max(id), 0) FROM users")
    int getMaxId();
}
