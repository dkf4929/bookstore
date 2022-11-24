package project.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.bookstore.dto.order.OrderFindDto;
import project.bookstore.dto.order.OrderSaveParamDto;
import project.bookstore.dto.order.OrderUpdateDto;
import project.bookstore.service.OrderService;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문 정보 검색
    @GetMapping("{memberId}/order")
    public List<OrderFindDto> findOrder(@PathVariable Long memberId) {
        return orderService.findOrder(memberId);
    }

    //주문하기
    @PostMapping("{memberId}/order/add")
    public void saveOrder(@PathVariable Long memberId, @RequestBody List<OrderSaveParamDto> dto) {
        orderService.save(memberId, dto);
    }

    //주문 정보 수정
    @PutMapping("{memberId}/order/{orderId}/edit")
    public List<OrderFindDto> updateOrder(@PathVariable Long memberId, @PathVariable Long orderId, @RequestBody List<OrderUpdateDto> dto) {
        return orderService.update(memberId, orderId, dto);
    }
}
