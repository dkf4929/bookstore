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

    public void save(Long memberId, List<Long> bookIds) {
        Member findMember = memberRepository.findById(memberId).orElseThrow();
        List<Book> books = bookRepository.findByBookIds(bookIds);
        Order order = Order.generateOrder(findMember, LocalDateTime.now());

        add(books, order);
    }

    private void add(List<Book> books, Order order) {
        for (Book book : books) {
            boolean isNotSaved = true;
            OrderBook orderBook = OrderBook.builder()
                    .book(book)
                    .order(order)
                    .build();

            if (isNotSaved) {
                orderRepository.save(order);
                isNotSaved = false;
            }
            orderBookRepository.save(orderBook);

            order.addOrderBook(orderBook);
            book.addOrderBook(orderBook);
        }
    }
}
