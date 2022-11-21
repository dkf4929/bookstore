package project.bookstore.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookUpdateDto {
    private String author;
    private String title;
    private String isbn;
    private int quantity;
    private int price;
    private String publisher;
}
