package io.bootify.order.repos;

import io.bootify.order.domain.Order;
import io.bootify.order.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findFirstByUser(User user);

}
