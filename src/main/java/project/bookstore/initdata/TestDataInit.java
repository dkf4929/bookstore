package project.bookstore.initdata;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.bookstore.dto.order.OrderSaveParamDto;
import project.bookstore.entity.Book;
import project.bookstore.entity.Member;
import project.bookstore.entity.embeddable.Address;
import project.bookstore.entity.embeddable.PrivateInfo;
import project.bookstore.repository.BookRepository;
import project.bookstore.repository.MemberRepository;
import project.bookstore.service.OrderService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Profile(value = "local")
@Component
@RequiredArgsConstructor
@Transactional
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final OrderService orderService;

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            Address address = new Address("서울", "도봉로-1234");

            PrivateInfo info = PrivateInfo.builder()
                    .name("member" + i)
                    .phoneNo("010-1111-2222")
                    .gender("M")
                    .birthDate(LocalDate.of(1990 + i, 01 + i, 01 + i))
                    .build();

            String role = null;

            if (i == 9) {
                role = "USER";
            } else {
                role = "ADMIN";
            }

            Member member = Member.builder()
                    .info(info)
                    .loginId(role + i)
                    .password("1234")
                    .address(address)
                    .build();

            memberRepository.save(member);
        }

        Book book1 = Book.builder()
                .title("The Title Market")
                .author("song")
                .isbn("9781644396421")
                .price(10000)
                .publisher("company")
                .quantity(100)
                .build();

        Book book2 = Book.builder()
                .title("지금 이 순간")
                .author("기욤 뮈소")
                .isbn("9788984372757")
                .price(10000)
                .publisher("none")
                .quantity(100)
                .build();

        bookRepository.save(book1);
        bookRepository.save(book2);

        List<OrderSaveParamDto> dto = new ArrayList<>();

        dto.add(new OrderSaveParamDto(1L, 5));
        dto.add(new OrderSaveParamDto(2L, 5));

        orderService.save(1L, dto);
    }
}
