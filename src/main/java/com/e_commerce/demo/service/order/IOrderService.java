package com.e_commerce.demo.service.order;

import com.e_commerce.demo.models.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(String userId);
    Order getOrderById(String orderId);
    List<Order> getOrdersByUser(String userId);
    void deleteOrder(String orderId);
    void updateOrderStatus(String orderId, String status);
}
