package project.bookstore.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(
        name = "ORDER_GENERATOR",
        sequenceName = "ORDER_SEQUENCES",
        initialValue = 1, allocationSize = 50
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_GENERATOR")
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "book", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderBook> books = new ArrayList<>();

    public static Order generateOrder(Member member, LocalDateTime orderDate) {
        return Order.builder()
                .member(member)
                .orderDate(orderDate)
                .build();
    }

    public void addOrderBook(OrderBook ... orderBooks) {
        for (OrderBook orderBook : orderBooks) {
            books.add(orderBook);
        }
    }
}
