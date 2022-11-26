package project.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import project.bookstore.api.ApiSearch;
import project.bookstore.dto.api.ApiResultDto;
import project.bookstore.dto.member.MemberFindDto;
import project.bookstore.dto.member.MemberFindParamDto;
import project.bookstore.dto.member.MemberSaveDto;
import project.bookstore.dto.member.MemberUpdateDto;
import project.bookstore.entity.Member;
import project.bookstore.entity.embeddable.Address;
import project.bookstore.entity.embeddable.PrivateInfo;
import project.bookstore.repository.MemberRepository;
import project.bookstore.exception.NoAuthorityException;
import project.bookstore.exception.NoSuchMemberException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;
    private final ApiSearch apiSearch;
    private final PasswordEncoder encoder;

    @Transactional(readOnly = false)
    public void save(MemberSaveDto dto) {
        List<ApiResultDto> resultDto = apiSearch.callApi("https://openapi.naver.com/v1/search/local?query=", dto.getAddress());
        Address address = new Address(resultDto.get(0).getContent2(), resultDto.get(0).getContent1());// 첫번째 요소 선택한다고 가정.

        PrivateInfo info = PrivateInfo.builder()
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .phoneNo(dto.getPhoneNo())
                .name(dto.getName())
                .build();

        Member member = Member.builder()
                .address(address)
                .loginId(dto.getLoginId())
                .password(encoder.encode(dto.getPassword()))
                .info(info)
                .build();

        repository.save(member);
    }

    // 관리자 / 직원 기능
    // 권한이 없는 사용자 접근 시 -> NoAuthorityException
    public Page<MemberFindDto> findAllByParam(Long memberId, Pageable pageable, MemberFindParamDto param) {
        Member member = repository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchMemberException("등록된 회원이 아닙니다.");
        });

        String role = member.getRole();

        if (authCheck(role)) {
            List<Member> members = repository.findAllByParam(param);

            // List<MemberFindDto> convert to List<MemberFindDto>

            List<MemberFindDto> all = dtoToList(members);

            if (all.size() == 0) {
                throw new NoSuchMemberException("조회 조건에 해당하는 회원이 없습니다.");
            }

            return new PageImpl<>(all, pageable, all.size());
        } else {
            throw new NoAuthorityException("해당 메뉴에 접근이 불가능한 권한입니다.");
        }
    }

    private Boolean authCheck(String role) {
        if (role.equals("ADMIN") || role.equals("EMPLOYEE")) {
            return true;
        }

        return false;
    }

    public MemberFindDto findById(Long memberId) {
        Member member = repository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchMemberException("등록되지 않은 회원입니다.");
        });

        return convertToMemberFindDto(member);
    }

    @Transactional(readOnly = false)
    public void delete(Long memberId) {
        Member member = repository.findById(memberId).get();
        repository.delete(member);
    }

    @Transactional(readOnly = false)
    public MemberFindDto update(Long memberId, MemberUpdateDto dto) {
        Member member = repository.findById(memberId).orElseThrow(
                () -> {
                    throw new NoSuchMemberException("등록된 회원이 아닙니다.");
                }
        );

        Member savedMember = repository.save(updateMember(dto, member));

        return convertToMemberFindDto(savedMember);
    }

    private Member updateMember(MemberUpdateDto dto, Member member) {
        if (StringUtils.hasText(dto.getPhoneNo())) {
            member.getInfo().updatePhoneNo(dto.getPhoneNo());
        }
        if (StringUtils.hasText(dto.getDetailAddress())) {
            member.updateAddress(member.getAddress().updateDetailAddress(dto.getDetailAddress()));
        }
        if (StringUtils.hasText(dto.getCity())) {
            member.updateAddress(member.getAddress().updateCity(dto.getCity()));
        }
        if (dto.getRole() != null) {
            member.updateRole(dto.getRole());
        }
        if (dto.getPassword() != null) {
            member.updatePassword(encoder.encode(dto.getPassword()));
        }

        return member;
    }

    private List<MemberFindDto> dtoToList(List<Member> members) {
        return members.stream()
                .map((m) -> convertToMemberFindDto(m)).collect(Collectors.toList());
    }

    private MemberFindDto convertToMemberFindDto(Member member) {
        return MemberFindDto.builder()
                .phoneNo(member.getInfo().getPhoneNo())
                .gender(member.getInfo().getGender())
                .name(member.getInfo().getName())
                .birthDate(member.getInfo().getBirthDate())
                .city(member.getAddress().getAddress())
                .detailAddress(member.getAddress().getRoadAddress())
                .role(member.getRole())
                .build();
    }


}
