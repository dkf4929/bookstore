package project.bookstore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bookstore.dto.order.OrderFindDto;
import project.bookstore.dto.order.OrderSaveParamDto;
import project.bookstore.dto.order.OrderUpdateDto;
import project.bookstore.entity.Book;
import project.bookstore.entity.Member;
import project.bookstore.entity.Order;
import project.bookstore.entity.OrderBook;
import project.bookstore.entity.enumclass.OrderStatus;
import project.bookstore.repository.BookRepository;
import project.bookstore.repository.MemberRepository;
import project.bookstore.repository.OrderBookRepository;
import project.bookstore.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final OrderBookRepository orderBookRepository;

    public void save(Long memberId, List<OrderSaveParamDto> dto) {
        Member findMember = memberRepository.findById(memberId).orElseThrow();
        List<Book> books = bookRepository.findByBookIds(dto);

        //편의상 주소, 핸드폰 번호는 멤버 객체에 저장되어 있는 정보를 사용.
        Order order = Order.generateOrder(findMember, LocalDateTime.now());


        add(books, order, dto);
    }

    private void add(List<Book> books, Order order, List<OrderSaveParamDto> dto) {
        for (var i = 0; i < dto.size(); i++) {
            boolean isNotSaved = true;
            OrderBook orderBook = OrderBook.builder()
                    .book(books.get(i))
                    .quantity(dto.get(i).getQuantity())
                    .order(order)
                    .build();

            if (isNotSaved) {
                orderRepository.save(order);
                isNotSaved = false;
            }
            orderBookRepository.save(orderBook);

            order.addOrderBook(orderBook);
            books.get(i).minusQuantity(dto.get(i).getQuantity());
            books.get(i).addOrderBook(orderBook);
        }
    }

    public List<OrderFindDto> findOrder(Long memberId) {
        return orderRepository.findOrder(memberId);
    }

    public List<OrderFindDto> update(Long memberId, Long orderId, List<OrderUpdateDto> dto) {
        List<OrderBook> orderBook = orderRepository.findOrderBook(orderId, dto);



        orderBook.forEach((o) -> {
            if (!o.getOrder().getOrderStatus().equals(OrderStatus.DELIVERY_READY)) {
                throw new IllegalArgumentException("배송 중이거나 배송이 완료된 상품은 수정이 불가능합니다.");
            }


            dto.forEach((d) -> {
                if (o.getBook().getId() == d.getBookId()) {
                    if (o.getQuantity() > d.getQuantity()) {
                        o.getBook().addQuantity(o.getQuantity() - d.getQuantity());
                    } else {
                        o.getBook().minusQuantity(d.getQuantity() - o.getQuantity());
                    }

                    if (d.getQuantity() == 0) {  //수량이 0일 경우 삭제.
                        orderBookRepository.deleteByBook(o.getBook());
                    } else {
                        o.updateQuantity(d.getQuantity());
                    }
                }
            });
        });

        return orderRepository.findOrder(memberId);
    }
}
