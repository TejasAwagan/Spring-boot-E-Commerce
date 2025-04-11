package com.e_commerce.demo.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.e_commerce.demo.Response.ApiResponse;
import com.e_commerce.demo.models.Order;
import com.e_commerce.demo.models.Product;
import com.e_commerce.demo.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    @Autowired
    IOrderService orderService;

//    @GetMapping("/{orderId}/getOrder")
//    public ResponseEntity<ApiResponse> getOrderById(@PathVariable String orderId){
//        try {
//            List<Order> order = orderService.getOrderById(orderId);
//            return ResponseEntity.ok(new ApiResponse("Order Found Successfully !", order));
//        }
//        catch(Exception e){
//            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
//        }
//    }

    @GetMapping("/{userId}/getOrdersByUserId")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable String userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId); // Correct method call

            if (orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No orders found for user ID: " + userId);
            }

            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching orders: " + e.getMessage());
        }
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable String orderId){
        try {
                orderService.deleteOrder(orderId);
                return ResponseEntity.ok(new ApiResponse("Delete Order success!", orderId));
        }
        catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/createOrder")
    public ResponseEntity<ApiResponse> createOrder(@PathVariable String userId){
        try{
            Order order = orderService.createOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Order created Successfully !",order));
        }
        catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{orderId}/updateStatus")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable String orderId,
            @RequestParam String status) {
        try {
            orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok("Order status updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update status: " + e.getMessage());
        }
    }
}
