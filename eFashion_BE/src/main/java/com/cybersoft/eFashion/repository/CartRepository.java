package com.cybersoft.eFashion.repository;

import com.cybersoft.eFashion.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface CartRepository extends JpaRepository<OrderItems,Integer> {
    @Query("select o from order_item o where user_id =?1")
    List<OrderItems> getOrderItemByUid(int id);

    List<OrderItems> getOrderItemById(int id);

    @Query("select quantity from order_item where product_id =?1 and user_id=?2")
    Integer getQuantityById(int proId,int userId);

    @Query("select o from order_item o where product_id =?1 and user_id=?2")
    List<OrderItems> findProductById(int proId, int userId);

    @Modifying
    @Transactional
    @Query("update order_item set quantity = quantity+1 where product_id =?1 and user_id=?2")
    Integer plusQuantity(int proId, int userId);

    @Modifying
    @Transactional
    @Query("update order_item set quantity = quantity-1 where product_id =?1 and user_id=?2")
    Integer subtractQuantity(int proId, int userId);

    @Modifying
    @Transactional
    @Query("delete from order_item where product_id =?1 and user_id=?2")
    Integer deleteProductById(int proId, int userId);


}
