package project.bookstore.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import project.bookstore.dto.order.OrderFindDto;
import project.bookstore.dto.order.OrderUpdateDto;
import project.bookstore.entity.*;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static project.bookstore.entity.QBook.*;
import static project.bookstore.entity.QMember.*;
import static project.bookstore.entity.QOrder.*;
import static project.bookstore.entity.QOrderBook.*;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    public OrderRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<OrderFindDto> findOrder(Long memberId) {
        List<Tuple> fetch = executeFind(memberId);

        List<OrderFindDto> orderFindDtos = new ArrayList<>();

        List<OrderBook> orderBooks = fetch.stream().map((s) -> s.get(orderBook)).collect(Collectors.toList());
        List<Member> members = fetch.stream().map((s) -> s.get(member)).collect(Collectors.toList());

        for (int i = 0; i < orderBooks.size(); i++) {
            OrderFindDto dto = getDto(orderBooks, members, i);

            orderFindDtos.add(dto);
        }

        return orderFindDtos;
    }

    @Override
    public List<OrderBook> findOrderBook(Long orderId, List<OrderUpdateDto> dto) {
        List<Long> collect = dto.stream().map((list) -> list.getBookId()).collect(Collectors.toList());

        return queryFactory
                .selectFrom(orderBook)
                .where(orderBook.order.id.eq(orderId).and(orderBook.book.id.in(collect)))
                .fetch();
    }

    private static OrderFindDto getDto(List<OrderBook> orderBooks, List<Member> members, int i) {
        return OrderFindDto.builder()
                .orderDate(orderBooks.get(i).getOrder().getOrderDate())
                .title(orderBooks.get(i).getBook().getTitle())
                .price(orderBooks.get(i).getBook().getPrice() * orderBooks.get(i).getQuantity())
                .quantity(orderBooks.get(i).getQuantity())
                .name(members.get(i).getInfo().getName())
                .detailAddress(members.get(i).getAddress().getDetailAddress())
                .phoneNo(members.get(i).getInfo().getPhoneNo())
                .build();
    }

    private List<Tuple> executeFind(Long memberId) {
        return queryFactory
                .select(orderBook, member)
                .from(orderBook)
                .join(orderBook.order.member, member)
                .where(orderBook.order.id.in(
                        JPAExpressions.select(order.id)
                                .from(order)
                                .where(order.member.id.in(memberId))
                ))
                .fetch();
    }
}
