package project.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.bookstore.service.OrderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("{memberId}/order/add")
    public void saveOrder(@PathVariable Long memberId, @RequestBody Object[] ids) {
        List<Long> bookIds = Arrays.stream(ids)
                .map((id) -> String.valueOf(id))
                .collect(Collectors.toList())
                .stream().map((id) -> Long.valueOf(id))
                .collect(Collectors.toList());

        orderService.save(memberId, bookIds);
    }
}
