package com.cybersoft.eFashion.service;

import com.cybersoft.eFashion.dto.AdminDTO;
import com.cybersoft.eFashion.dto.RoleDTO;
import com.cybersoft.eFashion.dto.UserDTO;
import com.cybersoft.eFashion.entity.Roles;
import com.cybersoft.eFashion.entity.Users;
import com.cybersoft.eFashion.repository.OrderRepository;
import com.cybersoft.eFashion.repository.ProductRepository;
import com.cybersoft.eFashion.repository.RoleRepository;
import com.cybersoft.eFashion.repository.UserRepository;
import com.cybersoft.eFashion.service.imp.AdminServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService implements AdminServiceImp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AdminDTO getInforAdminPage(int idAdmin) {
        AdminDTO adminDTO = new AdminDTO();
        Users users = userRepository.getUsersById(idAdmin);
        adminDTO.setId(users.getId());
        adminDTO.setEmail(users.getEmail());
        adminDTO.setName(users.getFullname());
        adminDTO.setAvatar(users.getAvatar());
        adminDTO.setTotalProducts(productRepository.findAll().size());
        adminDTO.setTotalOrder(orderRepository.findAll().size());
        adminDTO.setTotalUsers(userRepository.findAll().size());

        List<Users> list = userRepository.findAll();
        List<UserDTO> dtoList = new ArrayList<>();
        for (Users user: list) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setFullName(user.getFullname());
            userDTO.setAvatar(user.getAvatar());
            userDTO.setPhone(user.getPhone());
            userDTO.setAddress(user.getAddress());

            Roles roles = roleRepository.findById(user.getRoles().getId());
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(roles.getId());
            roleDTO.setName(roles.getName());
            roleDTO.setDesc(roles.getDescription());
            userDTO.setRoleDTO(roleDTO);

            dtoList.add(userDTO);
        }

        adminDTO.setList(dtoList);

        return adminDTO;
    }
}
