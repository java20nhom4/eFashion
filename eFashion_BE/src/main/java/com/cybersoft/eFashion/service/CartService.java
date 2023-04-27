package com.cybersoft.eFashion.service;

import com.cybersoft.eFashion.dto.OrderItemsDTO;
import com.cybersoft.eFashion.entity.OrderItems;
import com.cybersoft.eFashion.entity.Products;
import com.cybersoft.eFashion.entity.Users;
import com.cybersoft.eFashion.repository.CartRepository;
import com.cybersoft.eFashion.service.imp.CartServiceImp;
import com.cybersoft.eFashion.service.imp.FileStorageServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CartService implements CartServiceImp {
    @Value("${fileStorage.path}")
    private String parentFolder;

    @Autowired
    FileStorageServiceImp fileStorageServiceImp;
    @Autowired
    CartRepository cartRepository;
    @Override
    public List<OrderItemsDTO> getOrderItemByUid(int userId) {
        List<OrderItemsDTO> list= new ArrayList<>();
        for ( OrderItems orderItems :cartRepository.getOrderItemByUid(userId)){
            OrderItemsDTO orderItemsDTO = new OrderItemsDTO();
            orderItemsDTO.setId(orderItems.getId());
            String path  = parentFolder + "\\" + FolderType.Products.toString() + "\\" + orderItems.getProducts().getImage();
            orderItemsDTO.setImage(path);
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
    public Integer getQuantityById(int proId,int userId) {
       return cartRepository.getQuantityById(proId, userId);
    }

    @Override
    public boolean findProductById(int proId, int userId) {
        return cartRepository.findProductById(proId,userId).size()>0;
    }

    @Override
    public boolean addCart(int proId,int userId) {
        OrderItems orderItems = new OrderItems();
        orderItems.setQuantity(1);
        orderItems.setUserId(userId);
        orderItems.setProductId(proId);

        try{
            cartRepository.save(orderItems);
            return true;
        }catch (Exception e){
            return false;
        }
    }

   public Integer plusQuantity(int proId,int userId){
        return cartRepository.plusQuantity(proId,userId);
   }
    public Integer subtractQuantity(int proId,int userId){
        return cartRepository.subtractQuantity(proId,userId);
    }

    public Integer deleteProductById(int proId, int userId){
        return cartRepository.deleteProductById(proId,userId);
    }
}
