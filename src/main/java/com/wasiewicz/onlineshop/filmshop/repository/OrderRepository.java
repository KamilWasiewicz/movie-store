package com.wasiewicz.onlineshop.filmshop.repository;

import com.wasiewicz.onlineshop.filmshop.model.Order;
import com.wasiewicz.onlineshop.security.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findAllByUserId(AppUser appUser);
}
