package com.cybersoft.eFashion.repository;

import com.cybersoft.eFashion.entity.PlaceOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CheckoutRepository extends JpaRepository<PlaceOrders,Integer> {
    @Modifying
    @Transactional
    @Query("update users set phone = ?2, address=?4, fullname=?3 where id =?1")
    Integer updateUserById(int id,String phone, String address,String fullname);
}
