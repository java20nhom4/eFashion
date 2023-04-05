package com.cybersoft.eFashion.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "image")
    private String image;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "cate_id")
    private Category category;

    @OneToMany(mappedBy = "products")
    private Set<RatingProducts> listRatingProducts;

    @OneToMany(mappedBy = "products")
    private Set<OrderItems> listOrderItems;

    public Set<OrderItems> getListOrderItems() {
        return listOrderItems;
    }

    public void setListOrderItems(Set<OrderItems> listOrderItems) {
        this.listOrderItems = listOrderItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<RatingProducts> getListRatingProducts() {
        return listRatingProducts;
    }

    public void setListRatingProducts(Set<RatingProducts> listRatingProducts) {
        this.listRatingProducts = listRatingProducts;
    }
}
