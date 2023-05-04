package com.wasiewicz.onlineshop.filmshop.repository;

import com.wasiewicz.onlineshop.filmshop.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCityAndCountryAndStreetAndZipCode(String city, String country, String street, String zipCode);
}
