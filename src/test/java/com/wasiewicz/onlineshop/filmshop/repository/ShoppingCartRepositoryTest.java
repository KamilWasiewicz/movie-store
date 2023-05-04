package com.wasiewicz.onlineshop.filmshop.repository;

import com.wasiewicz.onlineshop.filmshop.model.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

@DataJpaTest
class ShoppingCartRepositoryTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @BeforeEach
    void setUp() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(1L);
        shoppingCart.setId(1L);
        shoppingCart.setTotalPrice(BigDecimal.TEN);
        shoppingCartRepository.save(shoppingCart);
    }

    @Test
    @DisplayName("Test findByUserId method")
    void findByUserId() {
        ShoppingCart foundShoppingCart = shoppingCartRepository.findByUserId(1L);

        Assertions.assertNotNull(foundShoppingCart);
        Assertions.assertEquals(1L, foundShoppingCart.getUserId());
        Assertions.assertEquals(2L,foundShoppingCart.getId());
        Assertions.assertEquals(BigDecimal.TEN,foundShoppingCart.getTotalPrice());
    }

    @Test
    @DisplayName("Test deleteByUserId method")
    void itShouldBeDeleteByUserId() {
        shoppingCartRepository.deleteByUserId(1L);

        Assertions.assertEquals(0, shoppingCartRepository.count());
    }
}