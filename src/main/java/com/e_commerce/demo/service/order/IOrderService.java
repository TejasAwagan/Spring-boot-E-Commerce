package com.e_commerce.demo.service.order;

import com.e_commerce.demo.models.Order;
import com.e_commerce.demo.models.Product;

import java.util.List;

public interface IOrderService {
    Order createOrder(String userId);

//    List<Order> getOrderById(String orderId);

    List<Order> getOrdersByUserId(String userId);

    void deleteOrder(String orderId);

    List<Product> getOrderedItems(String userId);

    void updateOrderStatus(String orderId, String status);
}
