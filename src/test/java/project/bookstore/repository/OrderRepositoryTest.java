package project.bookstore.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.bookstore.dto.order.OrderFindDto;
import project.bookstore.dto.order.OrderSaveParamDto;
import project.bookstore.dto.order.OrderUpdateDto;
import project.bookstore.entity.Book;
import project.bookstore.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class OrderRepositoryTest {
    @Autowired OrderRepository orderRepository;
    @Autowired BookRepository bookRepository;
    @Autowired OrderService service;

    @Test
    void findBy() {
        List<OrderFindDto> order = service.findOrder(1L);

        assertThat(order.size()).isEqualTo(2);

        for (OrderFindDto orderFindDto : order) {
            assertThat(orderFindDto.getQuantity()).isEqualTo(5);
        }
    }

    @Test
    @DisplayName("주문 시 책 수량 차감")
    void quantityTest() {
        Book book1 = bookRepository.findById(1L).orElseThrow();
        Book book2 = bookRepository.findById(2L).orElseThrow();

        assertThat(book1.getQuantity()).isEqualTo(95);
        assertThat(book2.getQuantity()).isEqualTo(95);
    }

    @Test
    @DisplayName("주문 수량 변경 시 책 수량 변경 반영되는지 여부 테스트")
    void updateTest() {
        List<OrderUpdateDto> dto = new ArrayList<>();
        dto.add(new OrderUpdateDto(1L, 10));
        dto.add(new OrderUpdateDto(2L, 3));

        service.update(1L, 1L, dto);

        Book book1 = bookRepository.findById(1L).orElseThrow();
        Book book2 = bookRepository.findById(2L).orElseThrow();

        assertThat(book1.getQuantity()).isEqualTo(90);
        assertThat(book2.getQuantity()).isEqualTo(97);
    }
}
