package com.wasiewicz.onlineshop.filmshop.service;

import com.wasiewicz.onlineshop.filmshop.dto.CartItemDto;
import com.wasiewicz.onlineshop.filmshop.dto.FilmDTO;
import com.wasiewicz.onlineshop.filmshop.mapper.CartItemDtoMapper;
import com.wasiewicz.onlineshop.filmshop.mapper.ShoppingCartDtoMapper;
import com.wasiewicz.onlineshop.filmshop.model.CartItem;
import com.wasiewicz.onlineshop.filmshop.model.Film;
import com.wasiewicz.onlineshop.filmshop.model.ShoppingCart;
import com.wasiewicz.onlineshop.filmshop.repository.CartItemRepository;
import com.wasiewicz.onlineshop.filmshop.repository.FilmRepository;
import com.wasiewicz.onlineshop.filmshop.repository.ShoppingCartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {

    @Mock
    private CartItemDtoMapper mockCartItemDtoMapper;
    @Mock
    private CartItemService mockCartItemService;
    @Mock
    private ShoppingCartService mockShoppingCartService;
    @Mock
    private ShoppingCartRepository mockShoppingCartRepository;
    @Mock
    private ShoppingCartDtoMapper mockShoppingCartDtoMapper;
    @Mock
    private FilmRepository mockFilmRepository;
    @Mock
    private CartItemRepository mockCartItemRepository;

    private CartItemService cartItemServiceUnderTest;

    @BeforeEach
    void setUp() {
        cartItemServiceUnderTest = new CartItemService(mockCartItemDtoMapper, mockShoppingCartService,
                mockShoppingCartRepository, mockShoppingCartDtoMapper, mockFilmRepository, mockCartItemRepository);
    }

    @Test
    void testAddFilmToCartItem() {
        // Setup
        final CartItemDto cartItemDto = new CartItemDto(0L,
                new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0, new BigDecimal("0.00"), 0,
                        "imageUrl"), 0, new BigDecimal("0.00"));
        final CartItemDto expectedResult = new CartItemDto(0L,
                new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0, new BigDecimal("0.00"), 0,
                        "imageUrl"), 0, new BigDecimal("0.00"));

        // Configure ShoppingCartService.getShoppingCart(...).
        final ShoppingCart shoppingCart = new ShoppingCart();
        final CartItem cartItem = new CartItem();
        cartItem.setId(0L);
        cartItem.setQuantity(0);
        cartItem.setPrice(new BigDecimal("0.00"));
        final Film film = new Film();
        film.setId(0L);
        film.setPrice(new BigDecimal("0.00"));
        cartItem.setFilm(film);
        shoppingCart.setCartItems(List.of(cartItem));
        shoppingCart.setTotalPrice(new BigDecimal("0.00"));
        when(mockShoppingCartService.getShoppingCart(0L)).thenReturn(shoppingCart);

        // Configure FilmRepository.findById(...).
        final Film film2 = new Film();
        film2.setId(0L);
        film2.setTitle("title");
        film2.setDirector("director");
        film2.setStars("stars");
        film2.setPrice(new BigDecimal("0.00"));

        // Configure CartItemDtoMapper.apply(...).
        final CartItemDto cartItemDto1 = new CartItemDto(0L,
                new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0, new BigDecimal("0.00"), 0,
                        "imageUrl"), 0, new BigDecimal("0.00"));
        final CartItem cartItem1 = new CartItem();
        cartItem1.setId(0L);
        cartItem1.setQuantity(0);
        cartItem1.setPrice(new BigDecimal("0.00"));
        final Film film3 = new Film();
        film3.setId(0L);
        film3.setPrice(new BigDecimal("0.00"));
        cartItem1.setFilm(film3);
        when(mockCartItemDtoMapper.apply(cartItem1)).thenReturn(cartItemDto1);

        // Run the test
        final CartItemDto result = cartItemServiceUnderTest.addFilmToCartItem(0L, 0L, cartItemDto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);

        // Confirm CartItemRepository.save(...).
        final CartItem entity = new CartItem();
        entity.setId(0L);
        entity.setQuantity(0);
        entity.setPrice(new BigDecimal("0.00"));
        final Film film4 = new Film();
        film4.setId(0L);
        film4.setPrice(new BigDecimal("0.00"));
        entity.setFilm(film4);
        verify(mockCartItemRepository).save(entity);

        // Confirm ShoppingCartRepository.save(...).
        final ShoppingCart entity1 = new ShoppingCart();
        final CartItem cartItem2 = new CartItem();
        cartItem2.setId(0L);
        cartItem2.setQuantity(0);
        cartItem2.setPrice(new BigDecimal("0.00"));
        final Film film5 = new Film();
        film5.setId(0L);
        film5.setPrice(new BigDecimal("0.00"));
        cartItem2.setFilm(film5);
        entity1.setCartItems(List.of(cartItem2));
        entity1.setTotalPrice(new BigDecimal("0.00"));
        verify(mockShoppingCartRepository).save(entity1);
    }

    @Test
    void testEditQuantity() {
        // Setup
        final CartItemDto expectedResult = new CartItemDto(0L,
                new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0, new BigDecimal("0.00"), 0,
                        "imageUrl"), 0, new BigDecimal("0.00"));

        // Configure CartItemRepository.findById(...).
        final CartItem cartItem1 = new CartItem();
        cartItem1.setId(0L);
        cartItem1.setQuantity(0);
        cartItem1.setPrice(new BigDecimal("0.00"));
        final Film film = new Film();
        film.setId(0L);
        film.setPrice(new BigDecimal("0.00"));
        cartItem1.setFilm(film);
        final Optional<CartItem> cartItem = Optional.of(cartItem1);
        when(mockCartItemRepository.findById(0L)).thenReturn(cartItem);

        // Configure CartItemRepository.save(...).
        final CartItem cartItem2 = new CartItem();
        cartItem2.setId(0L);
        cartItem2.setQuantity(0);
        cartItem2.setPrice(new BigDecimal("0.00"));
        final Film film1 = new Film();
        film1.setId(0L);
        film1.setPrice(new BigDecimal("0.00"));
        cartItem2.setFilm(film1);
        final CartItem entity = new CartItem();
        entity.setId(0L);
        entity.setQuantity(0);
        entity.setPrice(new BigDecimal("0.00"));
        final Film film2 = new Film();
        film2.setId(0L);
        film2.setPrice(new BigDecimal("0.00"));
        entity.setFilm(film2);
        when(mockCartItemRepository.save(entity)).thenReturn(cartItem2);

        // Configure CartItemDtoMapper.apply(...).
        final CartItem cartItem3 = new CartItem();
        cartItem3.setId(0L);
        cartItem3.setQuantity(0);
        cartItem3.setPrice(new BigDecimal("0.00"));
        final Film film3 = new Film();
        film3.setId(0L);
        film3.setPrice(new BigDecimal("0.00"));
        cartItem3.setFilm(film3);
        when(mockCartItemDtoMapper.apply(cartItem3)).thenReturn(expectedResult);

        // Run the test
        final CartItemDto result = cartItemServiceUnderTest.editQuantity(0L, 0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testEditQuantity_CartItemRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockCartItemRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> cartItemServiceUnderTest.editQuantity(0L, 0))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deleteCartItemFromShoppingCart_validCartIdAndItemId_itemRemoved() {
        // given
        Long cartId = 1L;
        Long cartItemId = 2L;

        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemId);

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(cartId);
        shoppingCart.setCartItems(cartItems);

        CartItemDto cartItemDto = new CartItemDto(cartItemId, null, 0, BigDecimal.ZERO);
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        cartItemDtos.add(cartItemDto);


        // when


        // then
        verify(mockShoppingCartRepository, times(0)).findById(cartId);
        verify(mockShoppingCartRepository, times(0)).save(shoppingCart);
        verify(mockShoppingCartDtoMapper, times(0)).apply(shoppingCart);
        assertEquals(1, shoppingCart.getCartItems().size());
    }

    @Test
    void testDeleteCartItemFromShoppingCart_ShoppingCartRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockShoppingCartRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> cartItemServiceUnderTest.deleteCartItemFromShoppingCart(0L, 0L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void testDeleteAllItemsFromShoppingCart() {
        CartItem cartItem = new CartItem();
        cartItem.setId(2L);
        cartItem.setQuantity(2);
        cartItem.setPrice(BigDecimal.TWO);
        mockCartItemRepository.save(cartItem);
        CartItemDto cartItemDto = mockCartItemDtoMapper.apply(cartItem);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        mockCartItemService.addFilmToCartItem(1L, 1L, cartItemDto);
        mockShoppingCartRepository.save(shoppingCart);
        mockShoppingCartDtoMapper.apply(shoppingCart);

        mockCartItemService.deleteAllItemsFromShoppingCart(shoppingCart.getId());

        assertEquals(0, shoppingCart.getCartItems().size());
    }


    @Test
    void testDeleteAllItemsFromShoppingCart_ShoppingCartRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockShoppingCartRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> cartItemServiceUnderTest.deleteAllItemsFromShoppingCart(0L))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
