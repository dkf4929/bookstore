package project.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.security.core.userdetails.UserDetails;
import project.bookstore.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    public Optional<Member> findByLoginId(String loginId);
}
