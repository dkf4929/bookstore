package project.bookstore.repository;

import project.bookstore.dto.member.MemberFindParamDto;
import project.bookstore.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findAllByParam(MemberFindParamDto param);
}
