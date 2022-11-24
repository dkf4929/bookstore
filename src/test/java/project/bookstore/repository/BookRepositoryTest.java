package project.bookstore.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.bookstore.dto.book.BookSearchResultDto;
import project.bookstore.entity.Book;
import project.bookstore.service.BookService;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class BookRepositoryTest {
    @Autowired
    BookService bookService;
    @Autowired BookRepository bookRepository;
    @Autowired EntityManager entityManager;

    @Test
    @Disabled("저장 및 저장된 요소 검색")
    void save() {
        Book book = Book.builder()
                .title("bookA")
                .author("song")
                .price(10000)
                .isbn("11111")
                .build();

        Book savedBook = bookRepository.save(book);
        Book findBook = bookRepository.findById(savedBook.getId()).orElseThrow();

        assertThat(savedBook).isEqualTo(findBook);
    }

    @Test
    @DisplayName("제목 또는 isbn으로 책 검색")
    void search() {
        List<BookSearchResultDto> search = bookService.search("The Title Market");

        BookSearchResultDto dto = search.stream().filter((s) -> s.getTitle().equals("The Title Market"))
                .findFirst()
                .orElseThrow();

        assertThat(dto.getTitle()).isEqualTo("The Title Market");

        List<BookSearchResultDto> search2 = bookService.search("9781644396421");

        BookSearchResultDto dto2 = search.stream().filter((s) -> s.getIsbn().equals("9781644396421"))
                .findFirst()
                .orElseThrow();

        assertThat(dto.getIsbn()).isEqualTo("9781644396421");
    }

    @Test
    @DisplayName("전체 조회")
    void findAll() {
        List<Book> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("수정")
    void update() {
        Book book = bookRepository.findById(1L).orElseThrow();
        book.updatePrice(5000);
        assertThat(book.getPrice()).isEqualTo(5000);
    }
}
