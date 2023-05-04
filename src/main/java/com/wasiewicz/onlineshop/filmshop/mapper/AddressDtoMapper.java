package com.wasiewicz.onlineshop.filmshop.mapper;

import com.wasiewicz.onlineshop.filmshop.dto.AddressDto;
import com.wasiewicz.onlineshop.filmshop.model.Address;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AddressDtoMapper implements Function<Address, AddressDto> {
    @Override
    public AddressDto apply(Address address) {
        return new AddressDto(
                address.getId(),
                address.getCountry(),
                address.getCity(),
                address.getZipCode(),
                address.getStreet()
        );
    }
}
