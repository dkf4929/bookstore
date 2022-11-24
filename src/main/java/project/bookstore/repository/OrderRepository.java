package project.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bookstore.dto.order.OrderFindDto;
import project.bookstore.dto.order.OrderUpdateDto;
import project.bookstore.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
}
