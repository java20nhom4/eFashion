package com.cybersoft.eFashion.entity;

import com.cybersoft.eFashion.entity.keys.KeyOrderItems;

import javax.persistence.*;

@Entity(name = "order_item")
public class OrderItems {
    @EmbeddedId
    private KeyOrderItems keys;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_id" , insertable = false, updatable = false)
    private Products products;

    public KeyOrderItems getKeys() {
        return keys;
    }

    public void setKeys(KeyOrderItems keys) {
        this.keys = keys;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }
}
