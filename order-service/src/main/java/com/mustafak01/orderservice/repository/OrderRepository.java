package com.mustafak01.orderservice.repository;

import com.mustafak01.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

    Optional<Order> findOrderByOrderNumber(String orderNumber);

}
