package project.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.bookstore.exception.NoSuchMemberException;
import project.bookstore.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class CustomMemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByLoginId(username).orElseThrow(
                () -> {throw new NoSuchMemberException("가입되지 않은 사용자입니다.");});
    }
}
