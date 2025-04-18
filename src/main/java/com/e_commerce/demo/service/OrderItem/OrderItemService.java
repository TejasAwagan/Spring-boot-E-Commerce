package com.e_commerce.demo.service.OrderItem;

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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderItemService implements IOrderItemService{

    @Autowired
    IOrderRepository orderRepository;

    @Autowired
    IProductRepository productRepository;

    @Autowired
    IOrderItemRepository orderItemRepository;

    @Autowired
    IUserRepository userRepository;


    @Override
    public void addProductToOrder(String userId, String productId, int quantity) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        List<Order> orders = orderRepository.findByUserId(userId);

        // Step 3: Look for an existing "CREATED" order
        Order order = orders.stream()
                .filter(o -> "CREATED".equalsIgnoreCase(o.getStatus()))
                .findFirst()
                .orElseGet(() -> {
                    // If no existing CREATED order, create a new one
                    Order newOrder = new Order();
                    newOrder.setOrderId(UUID.randomUUID().toString());
                    newOrder.setStatus("CREATED");
                    newOrder.setAmount(BigDecimal.ZERO);
                    newOrder.setOrderDate(System.currentTimeMillis());
                    newOrder.setUserId(userId);
                    return orderRepository.save(newOrder);
                });


        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        Optional<OrderItem> optionalItem = orderItemRepository.findByOrderIdAndProductId(order.getOrderId(), productId);

        if (optionalItem.isPresent()) {
            OrderItem item = optionalItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setTotalPrice(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            orderItemRepository.save(item);
        } else {
            OrderItem item = new OrderItem();
            item.setOrderItemId(UUID.randomUUID().toString());
            item.setOrderId(order.getOrderId());
            item.setProductId(product.getProductId());
            item.setPrice(BigDecimal.valueOf(product.getPrice()));
            item.setQuantity(quantity);
            item.setTotalPrice(BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity)));
            item.setStatus("PENDING");
            orderItemRepository.save(item);
        }


        updateOrderAmount(order);
    }
    @Override
    public void updateProductQuantity(String orderId, String productId, int quantity) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderItem item = orderItemRepository.findByOrderIdAndProductId(orderId, productId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setQuantity(quantity);
        item.setTotalPrice(item.getPrice().multiply(BigDecimal.valueOf(quantity)));
        orderItemRepository.save(item);

        updateOrderAmount(order);
    }

    @Override
    public void removeProductFromOrder(String orderId, String productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderItem item = orderItemRepository.findByOrderIdAndProductId(order.getOrderId(), product.getProductId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        orderItemRepository.delete(item);

        updateOrderAmount(order);
    }

    @Override
    public List<OrderItem> getAllItemsInOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return orderItemRepository.findByOrderId(orderId);
    }

    private void updateOrderAmount(Order order) {
        List<OrderItem> items = orderItemRepository.findByOrderId(order.getOrderId());
        BigDecimal total = items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setAmount(total);
        orderRepository.save(order);
    }
}
