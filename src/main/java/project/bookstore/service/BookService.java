package project.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import project.bookstore.api.BookSearchApi;
import project.bookstore.dto.book.BookFindDto;
import project.bookstore.dto.book.BookSaveDto;
import project.bookstore.dto.book.BookSearchResultDto;
import project.bookstore.dto.book.BookUpdateDto;
import project.bookstore.entity.Book;
import project.bookstore.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookSearchApi api;

    @Transactional(readOnly = false)
    public void save(BookSaveDto dto) {
        Book book = Book.builder()
                .quantity(dto.getQuantity())
                .isbn(dto.getIsbn())
                .publisher(dto.getPublisher())
                .price(dto.getPrice())
                .author(dto.getAuthor())
                .title(dto.getTitle())
                .build();

        bookRepository.save(book);
    }

    public List<BookSearchResultDto> search(String title) {
        return api.getBookInfo(title);
    }

    public BookFindDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow();

        return bookToDto(book);
    }

    @Transactional(readOnly = false)
    public BookFindDto update(Long id, BookUpdateDto dto) {
        Book findBook = bookRepository.findById(id).orElseThrow();

        Book book = updateBook(dto, findBook);
        Book savedBook = bookRepository.save(findBook);

        return bookToDto(savedBook);
    }

    public List<BookFindDto> findAll() {
        List<Book> books = bookRepository.findAll();

        return books.stream().map((b) ->
                bookToDto(b)).collect(Collectors.toList());
    }

    @Transactional(readOnly = false)
    public void delete(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();

        bookRepository.delete(book);
    }

    private Book updateBook(BookUpdateDto dto, Book findBook) {
        String author = dto.getAuthor();
        String isbn = dto.getIsbn();
        String title = dto.getTitle();
        String publisher = dto.getPublisher();
        int quantity = dto.getQuantity();
        int price = dto.getPrice();


        if (StringUtils.hasText(author)) {
            findBook.updateAuthor(author);
        }
        if (StringUtils.hasText(isbn)) {
            findBook.updateAuthor(isbn);
        }
        if (StringUtils.hasText(title)) {
            findBook.updateAuthor(title);
        }
        if (StringUtils.hasText(publisher)) {
            findBook.updateAuthor(publisher);
        }
        if (quantity != 0) {
            findBook.updateQuantity(quantity);
        }
        if (price != 0) {
            findBook.updatePrice(price);
        }

        return findBook;
    }

    private BookFindDto bookToDto(Book book) {
        return BookFindDto.builder()
                .author(book.getAuthor())
                .quantity(book.getQuantity())
                .price(book.getPrice())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .publisher(book.getPublisher())
                .build();
    }
}
