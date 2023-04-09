package com.cybersoft.eFashion.service.imp;

import com.cybersoft.eFashion.dto.OrderItemsDTO;

import java.util.List;

public interface CartServiceImp {
    List<OrderItemsDTO> getAllOrderItems();

    Integer getQuantityById(int id);

    boolean findProductById(int id);

    boolean addCart(OrderItemsDTO orderItemsDTO);

}
