package com.e_commerce.demo.service.order;

import com.e_commerce.demo.models.Order;
import com.e_commerce.demo.models.OrderItem;
import com.e_commerce.demo.models.Product;
import com.e_commerce.demo.models.User;
import com.e_commerce.demo.repository.IOrderItemRepository;
import com.e_commerce.demo.repository.IOrderRepository;
import com.e_commerce.demo.repository.IProductRepository;
import com.e_commerce.demo.repository.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService{
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    IProductRepository productRepository;

    @Autowired
    IOrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setUserId(userId);
        order.setStatus("CREATED");
        order.setAmount(BigDecimal.ZERO);
        order.setOrderDate(System.currentTimeMillis());
        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }


    @Override
    public List<Order> getOrdersByUserId(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        if (orders.isEmpty()) {
            throw new RuntimeException("No orders found for user ID: " + userId);
        }
        return orders;
    }



    @Override
    public void deleteOrder(String orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Product> getOrderedItems(String userId) {
        // Step 1: Fetch all orders by userId
        List<Order> orders = orderRepository.findByUserId(userId);

        if (orders.isEmpty()) {
            throw new RuntimeException("No orders found for user ID: " + userId);
        }

        // Step 2: Fetch all OrderItems for these orders
        List<OrderItem> orderItems = new ArrayList<>();
        for (Order order : orders) {
            List<OrderItem> items = orderItemRepository.findByOrderId(order.getOrderId());
            orderItems.addAll(items);
        }

        // Step 3: Fetch the associated products for each OrderItem
        List<Product> products = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            Product product = productRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found for ID: " + orderItem.getProductId()));
            products.add(product);
        }

        return products;
    }

    @Override
    public void updateOrderStatus(String orderId, String status) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

            order.setStatus(status);

            orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update order status: " + e.getMessage());
        }
    }


}
