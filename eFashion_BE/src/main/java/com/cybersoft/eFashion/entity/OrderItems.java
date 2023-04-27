package com.cybersoft.eFashion.entity;


import javax.persistence.*;
import java.util.Set;

@Entity(name = "order_item")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "quantity")
    private int quantity;

    @Column(name = "product_id")
    private int productId;
    @Column(name = "user_id")
    private int userId;
    @ManyToOne
    @JoinColumn(name = "product_id" , insertable = false, updatable = false)
    private Products products;
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users users;

    @OneToMany(mappedBy = "orderItems")
    private Set<PlaceOrders> placeOrders;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Set<PlaceOrders> getPlaceOrders() {
        return placeOrders;
    }

    public void setPlaceOrders(Set<PlaceOrders> placeOrders) {
        this.placeOrders = placeOrders;
    }
}
