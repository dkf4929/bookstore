package project.bookstore.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookSearchResultDto {
    private String title;
    private String author;
    private String isbn;
    private int price;
}
