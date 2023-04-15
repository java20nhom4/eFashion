package com.cybersoft.eFashion.service.imp;

import com.cybersoft.eFashion.dto.OrderItemsDTO;
import com.cybersoft.eFashion.dto.PlaceOrderDTO;

public interface CheckoutServiceImp {
    boolean addPlaceOrder(PlaceOrderDTO placeOrderDTO);
}
