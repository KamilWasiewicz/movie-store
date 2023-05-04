package com.wasiewicz.onlineshop.filmshop.mapper;

import com.wasiewicz.onlineshop.filmshop.dto.CartItemDto;
import com.wasiewicz.onlineshop.filmshop.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CartItemDtoMapper implements Function<CartItem, CartItemDto> {
    private final FilmDtoMapper filmDtoMapper;

    @Override
    public CartItemDto apply(CartItem cartItem) {
        return new CartItemDto(
                cartItem.getId(),
                filmDtoMapper.apply(cartItem.getFilm()),
                cartItem.getQuantity(),
                cartItem.getPrice()
        );
    }
}
