package project.bookstore.repository;

import project.bookstore.entity.Cart;

import java.util.List;
import java.util.Optional;

public interface CartRepositoryCustom {
    public List<Cart> findByMemberId(Long memberId);

    public Cart findByMemberIdAndBookId(Long memberId, Long bookId);
}
