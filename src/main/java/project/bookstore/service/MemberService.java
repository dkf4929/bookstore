package project.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import project.bookstore.dto.member.MemberFindDto;
import project.bookstore.dto.member.MemberFindParamDto;
import project.bookstore.dto.member.MemberSaveDto;
import project.bookstore.dto.member.MemberUpdateDto;
import project.bookstore.entity.Member;
import project.bookstore.entity.enumclass.AuthType;
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

    @Transactional(readOnly = false)
    public void save(MemberSaveDto dto) {
        Address address = new Address(dto.getCity(), dto.getDetailAddress());

        PrivateInfo info = PrivateInfo.builder()
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .phoneNo(dto.getPhoneNo())
                .name(dto.getName())
                .build();

        Member member = Member.builder()
                .address(address)
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
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

        AuthType authType = member.getAuthType();

        if (authCheck(authType)) {
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

    private Boolean authCheck(AuthType authType) {
        if (authType.equals(AuthType.ADMIN) || authType.equals(AuthType.INT_EMPLOYEE)) {
            return true;
        }

        return false;
    }

    public MemberFindDto findById(Long memberId) {
        Member member = repository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchMemberException("등록되지 않은 회원입니다.");
        });

        return getMemberFindDto(member);
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

        return getMemberFindDto(savedMember);
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
        if (dto.getAuthType() != null) {
            member.updateAuthType(dto.getAuthType());
        }

        return member;
    }

    private MemberFindDto getMemberFindDto(Member member) {
        return convertToMemberFindDto(member);
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
                .city(member.getAddress().getCity())
                .detailAddress(member.getAddress().getDetailAddress())
                .authType(member.getAuthType())
                .orders(member.getOrders())
                .build();
    }
}
