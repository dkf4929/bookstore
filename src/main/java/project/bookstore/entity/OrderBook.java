package project.bookstore.entity;

import lombok.*;
import project.bookstore.entity.base.SubEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OrderBook extends SubEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private int quantity;

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }
}
