package com.cybersoft.eFashion.service;

import com.cybersoft.eFashion.dto.PlaceOrderDTO;
import com.cybersoft.eFashion.entity.OrderItems;
import com.cybersoft.eFashion.entity.PlaceOrders;
import com.cybersoft.eFashion.entity.Products;
import com.cybersoft.eFashion.entity.Users;
import com.cybersoft.eFashion.repository.CheckoutRepository;
import com.cybersoft.eFashion.service.imp.CheckoutServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService implements CheckoutServiceImp {
    @Autowired
    CheckoutRepository checkoutRepository;
    @Override
    public boolean addPlaceOrder(PlaceOrderDTO placeOrderDTO) {
        PlaceOrders placeOrders = new PlaceOrders();

        OrderItems orderItems = new OrderItems();
        orderItems.setId(placeOrderDTO.getOrderItemId());
        placeOrders.setOrderItems(orderItems);
        Users users = new Users();
        users.setId(placeOrderDTO.getUserId());
        placeOrders.setUsers(users);

        try{
            checkoutRepository.save(placeOrders);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Integer updateUserById(int id,String phone,String address,String fullname){
        return checkoutRepository.updateUserById(id,phone,address,fullname);
    }
}
