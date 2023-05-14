package com.wasiewicz.onlineshop.filmshop.controller;

import com.wasiewicz.onlineshop.filmshop.dto.CartItemDto;
import com.wasiewicz.onlineshop.filmshop.dto.ShoppingCartDto;
import com.wasiewicz.onlineshop.filmshop.service.CartItemService;
import com.wasiewicz.onlineshop.filmshop.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@CrossOrigin
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCartDto> getUserShoppingCart(@PathVariable Long userId) {
        return ResponseEntity
                .ok(shoppingCartService
                        .getUserShoppingCart(userId));
    }

    @PostMapping("/{userId}/{filmId}")
    public ResponseEntity<CartItemDto> addFilmToCartItem(@PathVariable Long userId, @PathVariable Long filmId, @RequestBody CartItemDto cartItemDto) {
        return ResponseEntity
                .ok(cartItemService
                        .addFilmToCartItem(userId, filmId, cartItemDto));
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItemDto> editQuantity(@PathVariable Long cartItemId, @RequestBody CartItemDto cartItemDto) {
        return ResponseEntity
                .ok(cartItemService
                        .editQuantity(cartItemId, cartItemDto.quantity()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable Long userId) {
        shoppingCartService.deleteShoppingCart(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}/{cartItemId}")
    public ResponseEntity<ShoppingCartDto> deleteCartItemFromShoppingCart(@PathVariable Long cartId, @PathVariable Long cartItemId) {
        return ResponseEntity
                .ok(cartItemService.deleteCartItemFromShoppingCart(cartId, cartItemId));
    }

    @DeleteMapping("/{cartId}/clear")
    public ShoppingCartDto deleteAllItemFromShoppingCart(@PathVariable Long cartId) {
        return cartItemService.deleteAllItemsFromShoppingCart(cartId);
    }

}



