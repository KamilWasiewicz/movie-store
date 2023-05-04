package com.wasiewicz.onlineshop.filmshop.repository;
import com.wasiewicz.onlineshop.filmshop.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Test findByUserId method")
    void itShouldBeFindByUserId() {
        Order order = new Order();
        order.setUserId(1L);
        order.setOrderNumber("AGDSJFZXCV#$");
        orderRepository.save(order);

        Order foundOrder = orderRepository.findByUserId(1L);

        Assertions.assertNotNull(foundOrder);
        Assertions.assertEquals(order.getUserId(), foundOrder.getUserId());
        Assertions.assertEquals(order.getOrderNumber(),foundOrder.getOrderNumber());
    }

    @Test
    @DisplayName("Test deleteByUserId method")
    void itShouldBeDeleteByUserId() {
        Order order = new Order();
        order.setUserId(2L);
        order.setOrderNumber("AGDSJFZXCV#$");
        orderRepository.save(order);

        orderRepository.deleteByUserId(2L);

        Assertions.assertEquals(0, orderRepository.count());
    }
}
