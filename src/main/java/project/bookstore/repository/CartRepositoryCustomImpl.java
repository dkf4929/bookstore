package project.bookstore.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.bookstore.entity.Cart;
import project.bookstore.entity.QBook;
import project.bookstore.repository.CartRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static project.bookstore.entity.QCart.*;
import static project.bookstore.entity.QMember.member;

public class CartRepositoryCustomImpl implements CartRepositoryCustom {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    public CartRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<Cart> findByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(cart)
                .join(cart.book, QBook.book)
                .fetchJoin()
                .where(cart.member.id.eq(memberId))
                .fetch();
    }

    @Override
    public Cart findByMemberIdAndBookId(Long memberId, Long bookId) {
        return queryFactory
                .selectFrom(cart)
                .join(cart.member, member)
                .fetchJoin()
                .join(cart.book, QBook.book)
                .fetchJoin()
                .where(cart.book.id.eq(bookId).and(cart.member.id.eq(memberId)))
                .fetchOne();
    }
}
