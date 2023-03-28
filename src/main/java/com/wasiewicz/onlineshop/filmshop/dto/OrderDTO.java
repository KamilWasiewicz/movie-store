package com.wasiewicz.onlineshop.filmshop.dto;

import com.wasiewicz.onlineshop.filmshop.model.Order;
import com.wasiewicz.onlineshop.filmshop.model.OrderItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @NotNull
    private Long id;
    @NotNull
    private Long userId;
    @NotBlank
    private String orderNumber;
    @NotNull
    @Min(value = 0)
    private BigDecimal totalPrice;
    @NotBlank
    private LocalDateTime createdAt;

    private List<OrderItem> orderItems;

    public OrderDTO(Order order) {
        this.setId(order.getId());
    }
}
