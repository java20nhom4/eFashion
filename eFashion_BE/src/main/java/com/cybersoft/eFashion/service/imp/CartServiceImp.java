package com.cybersoft.eFashion.service.imp;

import com.cybersoft.eFashion.dto.OrderItemsDTO;

import java.util.List;

public interface CartServiceImp {
    List<OrderItemsDTO> getOrderItemByUid(int id);

    List<OrderItemsDTO> getOrderItemsById(int id);

    Integer getQuantityById(int proId, int userId);

    boolean findProductById(int proId, int userId);

    boolean addCart(int proId, int userId);

}
