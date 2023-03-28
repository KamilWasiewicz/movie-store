package com.wasiewicz.onlineshop.filmshop.dto;

import com.wasiewicz.onlineshop.filmshop.model.Film;
import com.wasiewicz.onlineshop.filmshop.model.Order;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsDTO {
    @NotNull
    private Long id;

    @NotNull
    @Min(value = 0)
    private int quantity;
    @NotNull
    @Min(value = 0)
    private BigDecimal price;
    @NotBlank
    private Date createdDate;

    private Order order;

    private Film film;

}
