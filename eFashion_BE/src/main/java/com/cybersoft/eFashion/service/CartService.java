package com.cybersoft.eFashion.service;

import com.cybersoft.eFashion.dto.OrderItemsDTO;
import com.cybersoft.eFashion.entity.OrderItems;
import com.cybersoft.eFashion.entity.Products;
import com.cybersoft.eFashion.repository.CartRepository;
import com.cybersoft.eFashion.service.imp.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CartService implements CartServiceImp {
    @Autowired
    CartRepository cartRepository;
    @Override
    public List<OrderItemsDTO> getAllOrderItems() {
        List<OrderItemsDTO> list= new ArrayList<>();
        for ( OrderItems orderItems :cartRepository.getAllOrderItems()){
            OrderItemsDTO orderItemsDTO = new OrderItemsDTO();
            orderItemsDTO.setId(orderItems.getId());
            orderItemsDTO.setQuantity(orderItems.getQuantity());
            orderItemsDTO.setName(orderItems.getProducts().getName());
            orderItemsDTO.setPrice(orderItems.getProducts().getPrice());
            orderItemsDTO.setProductId(orderItems.getProducts().getId());
            list.add(orderItemsDTO);
        }
        return list;
    }

    @Override
    public List<OrderItemsDTO> getOrderItemsById(int id) {
        List<OrderItemsDTO> list= new ArrayList<>();
        for ( OrderItems orderItems :cartRepository.getOrderItemById(id)){
            OrderItemsDTO orderItemsDTO = new OrderItemsDTO();
            orderItemsDTO.setId(orderItems.getId());
            orderItemsDTO.setQuantity(orderItems.getQuantity());
            orderItemsDTO.setName(orderItems.getProducts().getName());
            orderItemsDTO.setPrice(orderItems.getProducts().getPrice());
            orderItemsDTO.setProductId(orderItems.getProducts().getId());

            list.add(orderItemsDTO);
        }
        return list;
    }

    @Override
    public Integer getQuantityById(int id) {
       return cartRepository.getQuantityById(id);
    }

    @Override
    public boolean findProductById(int id) {
        return cartRepository.findProductById(id).size()>0;
    }

    @Override
    public boolean addCart(int proId) {
        OrderItems orderItems = new OrderItems();
        orderItems.setQuantity(1);
        orderItems.setProductId(proId);

        try{
            cartRepository.save(orderItems);
            return true;
        }catch (Exception e){
            return false;
        }
    }

   public Integer plusQuantity(int id){
        return cartRepository.plusQuantity(id);
   }
    public Integer subtractQuantity(int id){
        return cartRepository.subtractQuantity(id);
    }

    public Integer deleteProductById(int id){
        return cartRepository.deleteProductById(id);
    }
}
