package project.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.bookstore.dto.cart.CartFindDto;
import project.bookstore.dto.cart.CartSaveDto;
import project.bookstore.dto.cart.CartUpdateDto;
import project.bookstore.entity.Book;
import project.bookstore.entity.Cart;
import project.bookstore.entity.Member;
import project.bookstore.exception.NoSuchMemberException;
import project.bookstore.repository.BookRepository;
import project.bookstore.repository.CartRepository;
import project.bookstore.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public List<CartFindDto> findMyCart(Long memberId) {
        List<Cart> find = cartRepository.findByMemberId(memberId);

        return find.stream().map((cart) -> CartFindDto.builder()
                        .amount(cart.getAmount())
                        .book(cart.getBook())
                        .build())
                .collect(Collectors.toList());
    }

    public void save(Long memberId, Long bookId, CartSaveDto dto) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchMemberException("가입된 회원이 아닙니다.");
        });

        Book findBook = bookRepository.findById(bookId).get();

        cartRepository.save(Cart.builder()
                .member(findMember)
                .amount(dto.getAmount())
                .book(findBook)
                .build());
    }

    public List<CartFindDto> update(Long memberId, Long bookId, CartUpdateDto dto) {
        Cart findCart = cartRepository.findByMemberIdAndBookId(memberId, bookId);

        if (dto.getAmount() == 0) { //수량이 0일 시 삭제
            cartRepository.delete(findCart);
        } else {
            findCart.updateAmount(dto.getAmount());
            cartRepository.save(findCart);
        }
        return findMyCart(memberId);
    }
}
