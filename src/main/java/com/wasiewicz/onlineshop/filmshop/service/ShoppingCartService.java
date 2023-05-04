package com.wasiewicz.onlineshop.filmshop.service;

import com.wasiewicz.onlineshop.filmshop.dto.ShoppingCartDto;
import com.wasiewicz.onlineshop.filmshop.mapper.ShoppingCartDtoMapper;
import com.wasiewicz.onlineshop.filmshop.model.ShoppingCart;
import com.wasiewicz.onlineshop.filmshop.repository.CartItemRepository;
import com.wasiewicz.onlineshop.filmshop.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartDtoMapper shoppingCartDtoMapper;
    private final CartItemRepository cartItemRepository;

    public ShoppingCartDto getUserShoppingCart(Long userId) {
        return shoppingCartDtoMapper
                .apply(shoppingCartRepository
                        .findByUserId(userId));
    }

    public void deleteShoppingCart(Long userId) {
        shoppingCartRepository.deleteByUserId(userId);
    }

    public ShoppingCart getShoppingCart(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(userId);
            shoppingCartRepository.save(shoppingCart);
        }
        return shoppingCart;
    }

    public void deleteAllShoppingCart() {
        shoppingCartRepository.deleteAll();
        cartItemRepository.deleteAll();
    }
}