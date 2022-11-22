package project.bookstore.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import project.bookstore.entity.Book;
import project.bookstore.entity.Member;
import project.bookstore.entity.Order;
import project.bookstore.entity.OrderBook;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootTest
@Transactional
public class OrderRepositoryTest {
    @Autowired MemberRepository memberRepository;
    @Autowired BookRepository bookRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired EntityManager entityManager;

    @Test
    @Commit
    void save() {
        Member member = memberRepository.findById(1L).get();
        Book book = bookRepository.findById(1L).get();

        Order order = Order.builder()
                .orderDate(LocalDateTime.now())
                .member(member)
                .build();

        OrderBook build = OrderBook.builder()
                .book(book)
                .order(order)
                .build();

        entityManager.persist(order);
        entityManager.persist(build);
    }
}
