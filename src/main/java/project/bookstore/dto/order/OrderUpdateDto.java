package project.bookstore.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderUpdateDto {
    private Long bookId;
    private int quantity;
}
