package project.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.bookstore.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("{memberId}/order/add")
    public void saveOrder(@PathVariable Long memberId) {
        orderService.save(memberId, 2L);
    }
}
