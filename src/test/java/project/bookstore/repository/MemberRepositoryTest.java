package project.bookstore.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.bookstore.dto.MemberFindParamDto;
import project.bookstore.entity.Member;
import project.bookstore.entity.embeddable.Address;
import project.bookstore.entity.embeddable.PrivateInfo;
import project.bookstore.service.MemberService;
import project.bookstore.exception.NoSuchMemberException;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository repository;
    @Autowired MemberService memberService;

    @Test
    @DisplayName("회원 저장, 저장된 데이터 검증")
    void save() {
        Address address = new Address("서울", "도봉로-1234");
        PrivateInfo info = new PrivateInfo().builder()
                .name("memberA")
                .phoneNo("010-1111-2222")
                .gender("M")
                .birthDate(LocalDate.of(1990, 01, 01))
                .build();

        Member member = new Member().builder()
                .info(info)
                .loginId("ADM1")
                .password("1234")
                .address(address)
                .build();

        Member savedMember = repository.save(member);
        Member findMember = repository.findById(savedMember.getId()).get();

        assertThat(savedMember).isEqualTo(findMember);
    }

    @Test
    @DisplayName("저장된 멤버 파라미터로 조회")
    void findByParam() {
        MemberFindParamDto param = new MemberFindParamDto("member0", null, null, null);
        List<Member> members = repository.findAllByParam(param);

        assertThat(members.size()).isEqualTo(1);

        for (Member member : members) {
            assertThat(member.getInfo().getName()).isEqualTo("member0");
        }
    }

    @Test
    @DisplayName("등록되지 않은 사용자로 조회시 NoSuchMemberException 출력")
    void exTest() {
        MemberFindParamDto param2 = new MemberFindParamDto("member100", null, null, null);

        assertThatThrownBy(
                () -> memberService.findAllByParam(100L, null, param2))
                .isInstanceOf(NoSuchMemberException.class);
    }
}