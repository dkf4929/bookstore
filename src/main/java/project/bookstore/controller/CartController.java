package project.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.bookstore.dto.cart.CartFindDto;
import project.bookstore.dto.cart.CartSaveDto;
import project.bookstore.dto.cart.CartUpdateDto;
import project.bookstore.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{memberId}")
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart")
    public List<CartFindDto> findMyCart(@PathVariable Long memberId) {
        return cartService.findMyCart(memberId);
    }

    @PostMapping("/{bookId}/cart/add")
    public void save(@PathVariable Long memberId, @PathVariable Long bookId, @RequestBody CartSaveDto dto) {
        cartService.save(memberId, bookId, dto);
    }

    @PutMapping("/{bookId}/cart/edit")
    public List<CartFindDto> update(@PathVariable Long memberId, @PathVariable Long bookId, @RequestBody CartUpdateDto dto) {
        return cartService.update(memberId, bookId, dto);
    }
}
