package com.e_commerce.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    private String orderId;

    private String userId;

    private BigDecimal amount;

    private Long orderDate;

    private String status;
}