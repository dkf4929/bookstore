package project.bookstore.initdata;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.bookstore.entity.Member;
import project.bookstore.entity.authority.AuthType;
import project.bookstore.entity.embeddable.Address;
import project.bookstore.entity.embeddable.PrivateInfo;
import project.bookstore.repository.MemberRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Profile(value = "local")
@Component
@RequiredArgsConstructor
@Transactional
public class TestDataInit {

    private final MemberRepository repository;

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

            AuthType authType = null;

            if (i == 9) {
                authType = AuthType.USER;
            } else {
                authType = AuthType.ADMIN;
            }

            Member member = Member.builder()
                    .info(info)
                    .loginId(String.valueOf(authType) + i)
                    .password("1234")
                    .address(address)
                    .build();

            repository.save(member);
        }
    }
}
