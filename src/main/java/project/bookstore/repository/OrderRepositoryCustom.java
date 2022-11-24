package project.bookstore.repository;

import project.bookstore.dto.order.OrderFindDto;
import project.bookstore.dto.order.OrderUpdateDto;
import project.bookstore.entity.OrderBook;

import java.util.List;

public interface OrderRepositoryCustom {
    List<OrderFindDto> findOrder(Long memberId);

    List<OrderBook> findOrderBook(Long orderId, List<OrderUpdateDto> dto);
}
