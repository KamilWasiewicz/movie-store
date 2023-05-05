package com.wasiewicz.onlineshop.filmshop.service;

import com.wasiewicz.onlineshop.filmshop.dto.AddressDto;
import com.wasiewicz.onlineshop.filmshop.dto.OrderDto;
import com.wasiewicz.onlineshop.filmshop.mapper.OrderDtoMapper;
import com.wasiewicz.onlineshop.filmshop.model.Address;
import com.wasiewicz.onlineshop.filmshop.model.CartItem;
import com.wasiewicz.onlineshop.filmshop.model.Order;
import com.wasiewicz.onlineshop.filmshop.model.ShoppingCart;
import com.wasiewicz.onlineshop.filmshop.repository.AddressRepository;
import com.wasiewicz.onlineshop.filmshop.repository.OrderRepository;
import com.wasiewicz.onlineshop.filmshop.repository.ShoppingCartRepository;
import com.wasiewicz.onlineshop.security.model.AppUser;
import com.wasiewicz.onlineshop.security.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository mockOrderRepository;
    @Mock
    private ShoppingCartRepository mockShoppingCartRepository;
    @Mock
    private OrderDtoMapper mockOrderDtoMapper;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private AddressRepository mockAddressRepository;
    @Mock
    private ShoppingCartService mockShoppingCartService;

    private OrderService orderServiceUnderTest;

    @BeforeEach
    void setUp() {
        orderServiceUnderTest = new OrderService(mockOrderRepository, mockShoppingCartRepository, mockOrderDtoMapper,
                mockUserRepository, mockAddressRepository, mockShoppingCartService);
    }

    @Test
    void testGetOrders() {
        // Setup
        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(0L);
        shoppingCart.setUserId(0L);
        final CartItem cartItem = new CartItem();
        cartItem.setId(0L);
        cartItem.setQuantity(0);
        shoppingCart.setCartItems(List.of(cartItem));
        final Address address = new Address();
        address.setId(0L);
        address.setCountry("country");
        address.setCity("city");
        address.setZipCode("zipCode");
        address.setStreet("street");
        final OrderDto expectedResult =
                new OrderDto(0L, "orderNumber", 0L, shoppingCart, address, LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final OrderDto orderDto =
                new OrderDto(0L, "orderNumber", 0L, shoppingCart, address, LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        // Configure OrderRepository.findAll(...).
        final Order order = Order.builder()
                .orderNumber("orderNumber")
                .userId(0L)
                .shoppingCart(new ShoppingCart())
                .address(new Address())
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .build();
        when(mockOrderDtoMapper.apply(order)).thenReturn(orderDto);
        final List<Order>orders = List.of(order);
        when(mockOrderRepository.findAll()).thenReturn(orders);

        // Run the test
        final List<OrderDto> result = orderServiceUnderTest.getOrders();

        // Verify the results
        assertThat(result).isEqualTo(List.of(expectedResult));
    }

    @Test
    void testGetOrders_OrderRepositoryReturnsNoItems() {
        // Setup
        when(mockOrderRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<OrderDto> result = orderServiceUnderTest.getOrders();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetOrder() {
        // Setup
        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(0L);
        shoppingCart.setUserId(0L);
        final CartItem cartItem = new CartItem();
        cartItem.setId(0L);
        cartItem.setQuantity(0);
        shoppingCart.setCartItems(List.of(cartItem));
        final Address address = new Address();
        address.setId(0L);
        address.setCountry("country");
        address.setCity("city");
        address.setZipCode("zipCode");
        address.setStreet("street");
        final OrderDto expectedResult = new OrderDto(0L, "orderNumber", 0L, shoppingCart, address,
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockUserRepository.findById(0L)).thenReturn(Optional.of(AppUser.builder().build()));

        // Configure OrderRepository.findByUserId(...).
        final Order order = Order.builder()
                .orderNumber("orderNumber")
                .userId(0L)
                .shoppingCart(new ShoppingCart())
                .address(new Address())
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .build();
        when(mockOrderRepository.findByUserId(0L)).thenReturn(order);

        // Configure OrderDtoMapper.apply(...).
        final ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setId(0L);
        shoppingCart1.setUserId(0L);
        final CartItem cartItem1 = new CartItem();
        cartItem1.setId(0L);
        cartItem1.setQuantity(0);
        shoppingCart1.setCartItems(List.of(cartItem1));
        final Address address1 = new Address();
        address1.setId(0L);
        address1.setCountry("country");
        address1.setCity("city");
        address1.setZipCode("zipCode");
        address1.setStreet("street");
        final OrderDto orderDto = new OrderDto(0L, "orderNumber", 0L, shoppingCart1, address1,
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockOrderDtoMapper.apply(Order.builder()
                .orderNumber("orderNumber")
                .userId(0L)
                .shoppingCart(new ShoppingCart())
                .address(new Address())
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .build())).thenReturn(orderDto);

        // Run the test
        final OrderDto result = orderServiceUnderTest.getOrder(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetOrder_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> orderServiceUnderTest.getOrder(0L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void testPlaceOrder_ThrowsEntityExistsException() {
        // Setup
        final AddressDto addressDTO = new AddressDto(0L, "country", "city", "zipCode", "street");

        // Configure ShoppingCartRepository.findByUserId(...).
        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(0L);
        shoppingCart.setUserId(0L);
        final CartItem cartItem = new CartItem();
        cartItem.setId(0L);
        cartItem.setQuantity(0);
        shoppingCart.setCartItems(List.of(cartItem));
        when(mockShoppingCartRepository.findByUserId(0L)).thenReturn(shoppingCart);

        // Configure AddressRepository.findByCityAndCountryAndStreetAndZipCode(...).
        final List<Address> addresses = List.of(new Address(0L, "country", "city", "zipCode", "street"));
        when(mockAddressRepository.findByCityAndCountryAndStreetAndZipCode("city", "country", "street",
                "zipCode")).thenReturn(addresses);

        // Configure OrderRepository.findByUserId(...).
        final Order order = Order.builder()
                .orderNumber("orderNumber")
                .userId(0L)
                .shoppingCart(new ShoppingCart())
                .address(new Address())
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .build();
        when(mockOrderRepository.findByUserId(0L)).thenReturn(order);

        // Run the test
        assertThatThrownBy(() -> orderServiceUnderTest.placeOrder(0L, addressDTO))
                .isInstanceOf(EntityExistsException.class);
    }

    @Test
    void testPlaceOrder_ShoppingCartRepositoryReturnsNull() {
        // Setup
        final AddressDto addressDTO = new AddressDto(0L, "country", "city", "zipCode", "street");
        when(mockShoppingCartRepository.findByUserId(0L)).thenReturn(null);

        // Run the test
        assertThatThrownBy(() -> orderServiceUnderTest.placeOrder(0L, addressDTO))
                .isInstanceOf(EntityNotFoundException.class);
    }




    @Test
    void testCreateAddress() {
        // Setup
        final AddressDto addressDto = new AddressDto(0L, "country", "city", "zipCode", "street");
        final Address expectedResult = new Address(0L, "country", "city", "zipCode", "street");

        // Configure AddressRepository.findByCityAndCountryAndStreetAndZipCode(...).
        final List<Address> addresses = List.of(new Address(0L, "country", "city", "zipCode", "street"));
        when(mockAddressRepository.findByCityAndCountryAndStreetAndZipCode("city", "country", "street",
                "zipCode")).thenReturn(addresses);

        // Run the test
        final Address result = orderServiceUnderTest.createAddress(addressDto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }



    @Test
    void testDeleteOrders() {
        // Setup
        // Run the test
        orderServiceUnderTest.deleteOrders(0L);

        // Verify the results
        verify(mockShoppingCartService).deleteAllShoppingCart();
        verify(mockOrderRepository).deleteByUserId(0L);
    }
}
