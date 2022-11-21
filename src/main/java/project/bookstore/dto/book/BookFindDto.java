package project.bookstore.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BookFindDto {
    private String author;
    private String title;
    private String isbn;
    private int quantity;
    private int price;
    private String publisher;
}
