package project.bookstore.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.bookstore.entity.Book;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CartSaveDto {
    private int amount;
}
