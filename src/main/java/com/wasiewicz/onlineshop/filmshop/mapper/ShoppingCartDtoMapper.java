package com.wasiewicz.onlineshop.filmshop.mapper;

import com.wasiewicz.onlineshop.filmshop.dto.ShoppingCartDto;
import com.wasiewicz.onlineshop.filmshop.model.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartDtoMapper implements Function<ShoppingCart, ShoppingCartDto> {
    private final CartItemDtoMapper cartItemDtoMapper;
    @Override
    public ShoppingCartDto apply(ShoppingCart shoppingCart) {
        return new ShoppingCartDto(
                shoppingCart.getId(),
                shoppingCart.getUserId(),
                shoppingCart
                        .getCartItems()
                        .stream()
                        .map(cartItemDtoMapper)
                        .collect(Collectors.toList()),
                shoppingCart.getTotalPrice()
        );
    }
}
