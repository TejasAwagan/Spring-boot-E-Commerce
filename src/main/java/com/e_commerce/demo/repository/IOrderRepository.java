package com.e_commerce.demo.repository;

import com.e_commerce.demo.models.Order;
import com.e_commerce.demo.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUserId(String userId);
}
