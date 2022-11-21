package project.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bookstore.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {
}
