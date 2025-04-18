package com.e_commerce.demo.repository;

import com.e_commerce.demo.models.Order;
import com.e_commerce.demo.models.OrderItem;
import com.e_commerce.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderItemRepository extends JpaRepository<OrderItem,String> {
    Optional<OrderItem> findByOrderIdAndProductId(String orderId, String productId);

    List<OrderItem> findByOrderId(String orderId);
}
