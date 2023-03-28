package com.wasiewicz.onlineshop.filmshop.repository;

import com.wasiewicz.onlineshop.filmshop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
