package project.bookstore.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.bookstore.dto.order.OrderSaveParamDto;
import project.bookstore.entity.Book;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static project.bookstore.entity.QBook.*;

public class BookRepositoryCustomImpl implements BookRepositoryCustom{
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    public BookRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Book> findByBookIds(List<OrderSaveParamDto> dto) {
        return queryFactory
                .selectFrom(book)
                .where(book.id.in(
                        dto.stream()
                                .map((dtos) -> dtos.getBookId())
                                .collect(Collectors.toList())))
                .fetch();
    }
}
