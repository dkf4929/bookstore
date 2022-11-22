package project.bookstore.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import project.bookstore.dto.member.MemberFindParamDto;
import project.bookstore.entity.Member;
import project.bookstore.repository.MemberRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static project.bookstore.entity.QMember.*;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Member> findAllByParam(MemberFindParamDto param) {
        BooleanBuilder builder = new BooleanBuilder();

        return queryFactory.select(member)
                .from(member)
                .where(nameEq(param.getName()), genderEq(param.getGender()), birthBetween(param.getStartDate(), param.getEndDate()))
                .fetch();
    }

    private BooleanExpression nameEq(String name) {
        return name != null ? member.info.name.eq(name) : null;
    }

    private BooleanExpression genderEq(String gender) {
        return gender != null ? member.info.name.eq(gender) : null;
    }

    private BooleanExpression birthBetween(LocalDate start, LocalDate end) {
        return start != null && end != null ? member.info.birthDate.between(start, end) : null;
    }
}
