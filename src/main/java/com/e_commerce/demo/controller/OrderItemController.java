package com.e_commerce.demo.controller;

import com.e_commerce.demo.models.OrderItem;
import com.e_commerce.demo.service.OrderItem.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orderItems")
public class OrderItemController {
    @Autowired
    IOrderItemService orderItemService;


    @PostMapping("/add")
    public ResponseEntity<?> addProductToOrder(
            @RequestParam String userId,
            @RequestParam String productId,
            @RequestParam int quantity) {

        try {
            orderItemService.addProductToOrder(userId, productId, quantity);
            return ResponseEntity.ok("Product added to order successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProductQuantity(
            @RequestParam String orderId,
            @RequestParam String productId,
            @RequestParam int quantity) {

        try {
            orderItemService.updateProductQuantity(orderId, productId, quantity);
            return ResponseEntity.ok("Product quantity updated successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeProductFromOrder(
            @RequestParam String orderId,
            @RequestParam String productId) {

        try {
            orderItemService.removeProductFromOrder(orderId, productId);
            return ResponseEntity.ok("Product removed from order successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
        }
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllItemsInOrder(@RequestParam String orderId) {
        try {
            List<OrderItem> items = orderItemService.getAllItemsInOrder(orderId);
            return ResponseEntity.ok(items);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
        }
    }

}
