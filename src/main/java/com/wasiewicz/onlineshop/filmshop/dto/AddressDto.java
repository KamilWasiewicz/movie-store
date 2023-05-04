package com.wasiewicz.onlineshop.filmshop.dto;

public record AddressDto(
        Long id,
        String country,
        String city,
        String zipCode,
        String street
) {
}
