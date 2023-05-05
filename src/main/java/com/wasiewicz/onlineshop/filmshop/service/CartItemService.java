package com.wasiewicz.onlineshop.filmshop.service;

import com.wasiewicz.onlineshop.filmshop.dto.CartItemDto;
import com.wasiewicz.onlineshop.filmshop.dto.ShoppingCartDto;
import com.wasiewicz.onlineshop.filmshop.mapper.CartItemDtoMapper;
import com.wasiewicz.onlineshop.filmshop.mapper.ShoppingCartDtoMapper;
import com.wasiewicz.onlineshop.filmshop.model.CartItem;
import com.wasiewicz.onlineshop.filmshop.model.Film;
import com.wasiewicz.onlineshop.filmshop.model.ShoppingCart;
import com.wasiewicz.onlineshop.filmshop.repository.CartItemRepository;
import com.wasiewicz.onlineshop.filmshop.repository.FilmRepository;
import com.wasiewicz.onlineshop.filmshop.repository.ShoppingCartRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemDtoMapper cartItemDtoMapper;
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartDtoMapper shoppingCartDtoMapper;
    private final FilmRepository filmRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public CartItemDto addFilmToCartItem(Long userId, Long filmId, CartItemDto cartItemDto) {
        var shoppingCart = shoppingCartService.getShoppingCart(userId);
        CartItem cartItem = getOrCreateCartItem(shoppingCart, filmId);
        updateCartItemQuantity(cartItemDto, cartItem, shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        return cartItemDtoMapper.apply(cartItem);
    }

    private void updateCartItemQuantity(CartItemDto cartItemDto, CartItem cartItem, ShoppingCart shoppingCart) {
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + cartItemDto.quantity());
            Film film = cartItem.getFilm();
            cartItem.setPrice(film.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItemRepository.save(cartItem);
            setTotalPriceForShoppingCart(shoppingCart);
        }
    }

    private void setTotalPriceForShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setTotalPrice(
                shoppingCart
                        .getCartItems()
                        .stream()
                        .map(CartItem::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    private CartItem getOrCreateCartItem(ShoppingCart shoppingCart, Long filmId) {
        CartItem cartItem = shoppingCart
                .getCartItems()
                .stream()
                .filter(item -> item.getFilm().getId().equals(filmId))
                .findFirst()
                .orElse(null);

        if (cartItem == null) {
            Film film = filmRepository
                    .findById(filmId)
                    .orElseThrow(() -> new EntityNotFoundException("Film with id " + filmId + " not found"));

            cartItem = new CartItem();
            cartItem.setFilm(film);
            cartItem.setQuantity(0);
            cartItem.setPrice(BigDecimal.ZERO);
            cartItemRepository.save(cartItem);
            shoppingCart.getCartItems().add(cartItem);
        }
        return cartItem;
    }

    public CartItemDto editQuantity(Long cartItemId, int quantity) {
        var cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item with id " + cartItemId + " not found"));
        cartItem.setQuantity(quantity);
        return cartItemDtoMapper.apply(cartItemRepository.save(cartItem));
    }

    @Transactional
    public ShoppingCartDto deleteCartItemFromShoppingCart(Long cartId, Long cartItemId) {
        var shoppingCart = shoppingCartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Shopping cart with id " + cartId + " not found"));

        List<CartItem> cartItems = shoppingCart.getCartItems();

        boolean isRemoved = cartItems.removeIf(item -> item.getId().equals(cartItemId));

        if (isRemoved) {
            try {
                setTotalPriceForShoppingCart(shoppingCart);
                shoppingCartRepository.save(shoppingCart);
            } catch (Exception e) {
                throw new RuntimeException("Error occurred while saving shopping cart", e);
            }
            return shoppingCartDtoMapper.apply(shoppingCart);
        } else {
            throw new EntityNotFoundException("Cart item with id " + cartItemId + " not found in shopping cart with id " + cartId);
        }
    }

    @Transactional
    public ShoppingCartDto deleteAllItemsFromShoppingCart(Long cartId) {
        var shoppingCartOptional = shoppingCartRepository.findById(cartId);

        if (shoppingCartOptional.isPresent()) {
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            cartItemRepository.deleteAll(shoppingCart.getCartItems());
            shoppingCart.setCartItems(Collections.emptyList());
            shoppingCart.setTotalPrice(BigDecimal.ZERO);
            shoppingCartRepository.save(shoppingCart);
            return shoppingCartDtoMapper.apply(shoppingCart);
        } else {
            throw new EntityNotFoundException("Shopping cart with cart id " + cartId + " not found");
        }
    }
}
