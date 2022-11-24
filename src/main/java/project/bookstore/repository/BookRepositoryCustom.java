package project.bookstore.repository;

import project.bookstore.dto.order.OrderSaveParamDto;
import project.bookstore.entity.Book;

import java.util.List;

public interface BookRepositoryCustom {
    public List<Book> findByBookIds(List<OrderSaveParamDto> bookIds);
}
