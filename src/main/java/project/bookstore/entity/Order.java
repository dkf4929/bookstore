package project.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import project.bookstore.entity.base.SubEntity;
import project.bookstore.entity.enumclass.OrderStatus;

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
public class Order extends SubEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_GENERATOR")
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "book", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private List<OrderBook> books = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public static Order generateOrder(Member member, LocalDateTime orderDate) {
        return Order.builder()
                .member(member)
                .orderDate(orderDate)
                .orderStatus(OrderStatus.DELIVERY_READY)
                .books(new ArrayList<>()) // builder pattern -> nullpointerexception
                .build();
    }

    public void addOrderBook(OrderBook ... orderBooks) {
        for (OrderBook orderBook : orderBooks) {
            books.add(orderBook);
        }
    }
}
