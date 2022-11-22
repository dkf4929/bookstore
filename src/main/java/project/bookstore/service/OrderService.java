package project.bookstore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.bookstore.entity.Book;
import project.bookstore.entity.Member;
import project.bookstore.entity.Order;
import project.bookstore.entity.OrderBook;
import project.bookstore.repository.BookRepository;
import project.bookstore.repository.MemberRepository;
import project.bookstore.repository.OrderBookRepository;
import project.bookstore.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final OrderBookRepository orderBookRepository;

    public void save(Long memberId, Long bookId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();
        Order order = Order.generateOrder(findMember, LocalDateTime.now());

        OrderBook orderBook = OrderBook.builder()
                .book(book)
                .order(order)
                .build();

        order.addOrderBook(orderBook);
        book.addOrderBook(orderBook);

        orderRepository.save(order);
        orderBookRepository.save(orderBook);
    }
}
