package project.bookstore.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BookSaveDto {
    @NotEmpty
    private String author;

    @NotEmpty
    private String title;

    @NotEmpty
    private String isbn;

    @NotNull
    private int quantity;

    @NotNull
    private int price;

    private String publisher;
}
