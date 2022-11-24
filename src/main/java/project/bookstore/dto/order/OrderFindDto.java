package project.bookstore.dto.order;

import lombok.*;
import project.bookstore.entity.OrderBook;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
@ToString
public class OrderFindDto {
    private String name;
    private String detailAddress;
    private String phoneNo;
    private int quantity;
    private LocalDateTime orderDate;
    private String title;
    private int price;
}
