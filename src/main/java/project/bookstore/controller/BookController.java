package project.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.bookstore.api.ApiParam;
import project.bookstore.api.BookSearchApi;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookSearchApi api;

    @GetMapping("/search")
    public String searchByName(@RequestBody String title) {
        return api.getBookInfo(title);
    }
}
