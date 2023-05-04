package com.wasiewicz.onlineshop.filmshop.service;

import com.wasiewicz.onlineshop.filmshop.dto.AddressDto;
import com.wasiewicz.onlineshop.filmshop.dto.OrderDto;
import com.wasiewicz.onlineshop.filmshop.mapper.OrderDtoMapper;
import com.wasiewicz.onlineshop.filmshop.model.Address;
import com.wasiewicz.onlineshop.filmshop.model.Order;
import com.wasiewicz.onlineshop.filmshop.repository.AddressRepository;
import com.wasiewicz.onlineshop.filmshop.repository.OrderRepository;
import com.wasiewicz.onlineshop.filmshop.repository.ShoppingCartRepository;
import com.wasiewicz.onlineshop.security.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderDtoMapper orderDtoMapper;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ShoppingCartService shoppingCartService;

    public List<OrderDto> getOrders() {
        return orderRepository
                .findAll()
                .stream()
                .map(orderDtoMapper)
                .collect(Collectors.toList());
    }

    public OrderDto getOrder(Long userId) {
        if (userRepository.findById(userId).isEmpty())
            throw new EntityNotFoundException("User with id " + userId + " not found");
        return orderDtoMapper.apply(orderRepository.findByUserId(userId));
    }

    public OrderDto placeOrder(Long userId, AddressDto addressDTO) {
        var shoppingCart = shoppingCartRepository.findByUserId(userId);
        if (shoppingCart == null)
            throw new EntityNotFoundException("Shopping carts with user id " + userId + " not found");
        var createdAddress = createAddress(addressDTO);
        Order existingorder = orderRepository.findByUserId(userId);
        if (!(existingorder == null))
            throw new EntityExistsException("Order with user id " + userId + " already exist");
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setUserId(userId);
        order.setShoppingCart(shoppingCart);
        order.setAddress(createdAddress);
        order.setCreatedAt(LocalDateTime.now());

        orderRepository.save(order);
        return orderDtoMapper.apply(order);
    }

    @Transactional
    public Address createAddress(AddressDto addressDto) {
        List<Address> existingAddresses = addressRepository.findByCityAndCountryAndStreetAndZipCode(
                addressDto.city(),
                addressDto.country(),
                addressDto.street(),
                addressDto.zipCode()
        );
        if (!existingAddresses.isEmpty()) {
            return existingAddresses.get(0);
        }
        Address address = new Address();
        address.setCountry(addressDto.country());
        address.setCity(addressDto.city());
        address.setZipCode(addressDto.zipCode());
        address.setStreet(addressDto.street());
        return addressRepository.save(address);
    }

    private String generateOrderNumber() {
        return "ORDER - " + UUID
                .randomUUID()
                .toString()
                .replaceAll("[^a-zA-Z]", "")
                .substring(0, 10)
                .toUpperCase();
    }

    @Transactional
    public void deleteOrders(Long userId) {
        shoppingCartService.deleteAllShoppingCart();
        orderRepository.deleteByUserId(userId);
    }
}