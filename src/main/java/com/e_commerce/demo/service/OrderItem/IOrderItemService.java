package com.e_commerce.demo.service.OrderItem;

import com.e_commerce.demo.models.OrderItem;

import java.util.List;

public interface IOrderItemService {

    void addProductToOrder(String orderId, String productId, int quantity);

    void updateProductQuantity(String orderId, String productId, int quantity);

    void removeProductFromOrder(String orderId, String productId);

    List<OrderItem> getAllItemsInOrder(String orderId);}
