package project.bookstore.entity;

import lombok.*;
import project.bookstore.entity.base.BaseEntity;
import project.bookstore.entity.base.SubEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@SequenceGenerator(
        name = "BOOK_GENERATOR",
        sequenceName = "BOOK_SEQUENCES",
        initialValue = 1, allocationSize = 50
)
public class Book extends SubEntity {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_GENERATOR")
    private Long id;

    @NotNull
    private String author;

    @NotNull
    private String title;

    @NotNull
    @Column(unique = true)
    private String isbn;

    private int quantity;

    @NotNull
    private int price;

    private String publisher;

    public void updateAuthor(String author) {
        this.author = author;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updatePrice(int price) {
        this.price = price;
    }

    public void updatePublisher(String publisher) {
        this.publisher = publisher;
    }
}
