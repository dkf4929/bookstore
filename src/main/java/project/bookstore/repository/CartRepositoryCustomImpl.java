package project.bookstore.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.bookstore.entity.Cart;
import project.bookstore.entity.QCart;

import javax.persistence.EntityManager;
import java.util.Optional;

import static project.bookstore.entity.QCart.*;

public class CartRepositoryCustomImpl implements CartRepositoryCustom {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    public CartRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public Optional<Cart> findByMemberId(Long memberId) {
        return Optional.of(queryFactory
                .selectFrom(cart)
                .where(cart.member.id.eq(memberId))
                .fetchOne());
    }
}
