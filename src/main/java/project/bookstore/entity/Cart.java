package project.bookstore.entity;

import lombok.*;
import project.bookstore.entity.base.BaseEntity;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "CART_GENERATOR",
        sequenceName = "CART_SEQUENCES",
        initialValue = 1, allocationSize = 50
)
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CART_GENERATOR")
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", unique = true)
    private Book book;

    private int amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateAmount(int amount) {
        this.amount = amount;
    }
}
