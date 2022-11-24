package project.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bookstore.entity.Book;
import project.bookstore.entity.OrderBook;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
    public void deleteByBook(Book book);
}
