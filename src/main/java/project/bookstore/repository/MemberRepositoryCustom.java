package project.bookstore.repository;

import project.bookstore.dto.member.MemberFindParamDto;
import project.bookstore.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {
    List<Member> findAllByParam(MemberFindParamDto param);

    Optional<Member> findByLoginIdAndPassword(String loginId, String password);
}
