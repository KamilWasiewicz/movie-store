package com.wasiewicz.onlineshop.filmshop.controller;

import com.wasiewicz.onlineshop.filmshop.dto.AddressDto;
import com.wasiewicz.onlineshop.filmshop.dto.OrderDto;
import com.wasiewicz.onlineshop.filmshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDto>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(Collections.singletonList(orderService.getOrder(userId)));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody AddressDto addressDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(orderService.placeOrder(userId, addressDTO));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteOrders(@PathVariable Long userId) {
        orderService.deleteOrders(userId);
        return ResponseEntity.ok().build();
    }

}