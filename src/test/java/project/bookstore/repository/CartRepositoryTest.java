package project.bookstore.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.bookstore.dto.cart.CartFindDto;
import project.bookstore.dto.cart.CartSaveDto;
import project.bookstore.dto.cart.CartUpdateDto;
import project.bookstore.entity.Cart;
import project.bookstore.service.CartService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class CartRepositoryTest {
    @Autowired CartService cartService;
    @Autowired CartRepository cartRepository;

    @BeforeEach
    void before() {
        cartService.save(1L, 1L, CartSaveDto.builder().amount(3).build());
    }

    @Test
    void save() {
        List<Cart> find = cartRepository.findByMemberId(1L);
        assertThat(find.size()).isEqualTo(1);
    }

    @Test
    void update() {
        List<CartFindDto> cart = cartService.update(1L, 1L, new CartUpdateDto(0));
        assertThat(cart.size()).isEqualTo(0);
    }
}
