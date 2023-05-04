package com.wasiewicz.onlineshop.filmshop.repository;

import com.wasiewicz.onlineshop.filmshop.model.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Test findByCityAndCountryAndStreetAndZipCode method")
    void testFindByCityAndCountryAndStreetAndZipCode() {
        Address address1 = new Address(1L, "Poland", "Warsaw", "00-001", "Main Street");
        Address address2 = new Address(2L, "Poland", "Cracow", "00-002", "Second Street");
        Address address3 = new Address(3L, "Poland", "Warsaw", "00-003", "Third Street");

        addressRepository.saveAll(List.of(address1, address2, address3));

        List<Address> result = addressRepository.findByCityAndCountryAndStreetAndZipCode("Warsaw", "Poland", "Main Street", "00-001");
        assertEquals(1, result.size());
        assertEquals("Warsaw", result.get(0).getCity());
        assertEquals("Poland", result.get(0).getCountry());
        assertEquals("Main Street", result.get(0).getStreet());
        assertEquals("00-001", result.get(0).getZipCode());
    }
}
