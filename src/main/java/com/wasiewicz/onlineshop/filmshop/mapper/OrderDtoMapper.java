package com.wasiewicz.onlineshop.filmshop.mapper;

import com.wasiewicz.onlineshop.filmshop.dto.OrderDto;
import com.wasiewicz.onlineshop.filmshop.model.Order;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class OrderDtoMapper implements Function<Order, OrderDto> {
    @Override
    public OrderDto apply(Order order) {
        return new OrderDto(
                order.getId(),
                order.getOrderNumber(),
                order.getUserId(),
                order.getShoppingCart(),
                order.getAddress(),
                order.getCreatedAt()
        );
    }
}
