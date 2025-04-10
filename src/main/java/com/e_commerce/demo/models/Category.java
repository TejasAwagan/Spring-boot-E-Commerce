package com.e_commerce.demo.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    private String categoryId;

    private String categoryName;

    private String status;

    public Category() {
    }

    public Category(String categoryId, String categoryName, String status) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.status = status;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
