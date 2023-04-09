package com.cybersoft.eFashion.entity;


import javax.persistence.*;

@Entity(name = "order_item")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "quantity")
    private int quantity;


    @ManyToOne
    @JoinColumn(name = "product_id" , insertable = false, updatable = false)
    private Products products;


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
}
