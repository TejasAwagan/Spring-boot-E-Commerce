package com.e_commerce.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@Data
public class OrderItem {

    @Id
    private String orderItemId;

    private String orderId;

    private Product product;


    private BigDecimal price;

    private int quantity;

    private BigDecimal totalPrice;

    private String status;
}
