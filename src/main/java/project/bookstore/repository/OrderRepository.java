package project.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bookstore.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
