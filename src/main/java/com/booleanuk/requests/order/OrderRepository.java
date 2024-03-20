package com.booleanuk.requests.order;

import com.booleanuk.requests.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
//    List<Order> findByUser(User user);
}
