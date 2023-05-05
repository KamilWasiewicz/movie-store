package com.wasiewicz.onlineshop.filmshop.service;

import com.wasiewicz.onlineshop.filmshop.dto.CartItemDto;
import com.wasiewicz.onlineshop.filmshop.dto.FilmDTO;
import com.wasiewicz.onlineshop.filmshop.dto.ShoppingCartDto;
import com.wasiewicz.onlineshop.filmshop.mapper.ShoppingCartDtoMapper;
import com.wasiewicz.onlineshop.filmshop.model.CartItem;
import com.wasiewicz.onlineshop.filmshop.model.ShoppingCart;
import com.wasiewicz.onlineshop.filmshop.repository.CartItemRepository;
import com.wasiewicz.onlineshop.filmshop.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository mockShoppingCartRepository;
    @Mock
    private ShoppingCartDtoMapper mockShoppingCartDtoMapper;
    @Mock
    private CartItemRepository mockCartItemRepository;

    private ShoppingCartService shoppingCartServiceUnderTest;

    @BeforeEach
    void setUp() {
        shoppingCartServiceUnderTest = new ShoppingCartService(mockShoppingCartRepository, mockShoppingCartDtoMapper,
                mockCartItemRepository);
    }

    @Test
    void testGetUserShoppingCart() {
        // Setup
        final ShoppingCartDto expectedResult = new ShoppingCartDto(0L, 0L, List.of(new CartItemDto(0L,
                new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0, new BigDecimal("0.00"), 0,
                        "imageUrl"), 0, new BigDecimal("0.00"))), new BigDecimal("0.00"));

        // Configure ShoppingCartRepository.findByUserId(...).
        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(0L);
        shoppingCart.setUserId(0L);
        final CartItem cartItem = new CartItem();
        cartItem.setId(0L);
        cartItem.setQuantity(0);
        shoppingCart.setCartItems(List.of(cartItem));
        when(mockShoppingCartRepository.findByUserId(0L)).thenReturn(shoppingCart);

        // Configure ShoppingCartDtoMapper.apply(...).
        final ShoppingCartDto shoppingCartDto = new ShoppingCartDto(0L, 0L, List.of(new CartItemDto(0L,
                new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0, new BigDecimal("0.00"), 0,
                        "imageUrl"), 0, new BigDecimal("0.00"))), new BigDecimal("0.00"));
        final ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setId(0L);
        shoppingCart1.setUserId(0L);
        final CartItem cartItem1 = new CartItem();
        cartItem1.setId(0L);
        cartItem1.setQuantity(0);
        shoppingCart1.setCartItems(List.of(cartItem1));
        when(mockShoppingCartDtoMapper.apply(shoppingCart1)).thenReturn(shoppingCartDto);

        // Run the test
        final ShoppingCartDto result = shoppingCartServiceUnderTest.getUserShoppingCart(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testDeleteShoppingCart() {
        // Setup
        // Run the test
        shoppingCartServiceUnderTest.deleteShoppingCart(0L);

        // Verify the results
        verify(mockShoppingCartRepository).deleteByUserId(0L);
    }

    @Test
    void testGetShoppingCart() {
        // Setup
        final ShoppingCart expectedResult = new ShoppingCart();
        expectedResult.setId(0L);
        expectedResult.setUserId(0L);
        final CartItem cartItem = new CartItem();
        cartItem.setId(0L);
        cartItem.setQuantity(0);
        expectedResult.setCartItems(List.of(cartItem));

        // Configure ShoppingCartRepository.findByUserId(...).
        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(0L);
        shoppingCart.setUserId(0L);
        final CartItem cartItem1 = new CartItem();
        cartItem1.setId(0L);
        cartItem1.setQuantity(0);
        shoppingCart.setCartItems(List.of(cartItem1));
        when(mockShoppingCartRepository.findByUserId(0L)).thenReturn(shoppingCart);

        // Run the test
        final ShoppingCart result = shoppingCartServiceUnderTest.getShoppingCart(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testDeleteAllShoppingCart() {
        // Setup
        // Run the test
        shoppingCartServiceUnderTest.deleteAllShoppingCart();

        // Verify the results
        verify(mockShoppingCartRepository).deleteAll();
        verify(mockCartItemRepository).deleteAll();
    }
}
