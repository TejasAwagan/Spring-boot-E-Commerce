package com.e_commerce.demo.service.order;

import com.e_commerce.demo.dto.OrderDto;
import com.e_commerce.demo.models.Order;
import com.e_commerce.demo.models.User;
import com.e_commerce.demo.repository.IOrderRepository;
import com.e_commerce.demo.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService implements IOrderService{
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Order createOrder(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setUserId(userId);
        order.setStatus("CREATED");
        order.setAmount(BigDecimal.ZERO); // initially zero
        order.setOrderDate(System.currentTimeMillis());
        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    @Override
    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(()->
                        new RuntimeException("Order not Found"));
    }

    @Override
    public List<Order> getOrdersByUser(String userId) {
        return orderRepository.findByUserUserId(userId);
    }

    @Override
    public void deleteOrder(String orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public void updateOrderStatus(String orderId, String status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        orderRepository.save(order);
    }

}
