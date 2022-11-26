package project.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.bookstore.dto.book.BookFindDto;
import project.bookstore.dto.book.BookSaveDto;
import project.bookstore.dto.api.ApiResultDto;
import project.bookstore.dto.book.BookUpdateDto;
import project.bookstore.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    //사용자 기능 - interceptor check x
    @GetMapping("/search")
    public List<ApiResultDto> searchByName(@RequestBody String title) {
        String url = "https://openapi.naver.com/v1/search/book?query=";
        return bookService.search(url, title);
    }

    @PostMapping("/add")
    public void save(@RequestBody @Validated BookSaveDto dto) {
        bookService.save(dto);
    }

    @GetMapping("/{bookId}")
    public BookFindDto findById(@PathVariable(name = "bookId") Long id) {
        return bookService.findById(id);
    }

    @GetMapping
    public List<BookFindDto> findAll() {
        return bookService.findAll();
    }

    @PutMapping("/{bookId}/edit")
    public BookFindDto update(@PathVariable(name = "bookId") Long id, @RequestBody BookUpdateDto dto) {
        return bookService.update(id, dto);
    }

    @DeleteMapping("/{bookId}")
    public String delete(@PathVariable Long bookId) {
        bookService.delete(bookId);
        return "삭제 완료!";
    }
}
