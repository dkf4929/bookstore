package project.bookstore.interceptor;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import project.bookstore.entity.Member;
import project.bookstore.entity.enumclass.AuthType;
import project.bookstore.exception.NoAuthorityException;
import project.bookstore.exception.NoSuchMemberException;
import project.bookstore.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("권한 체크 인터셉터 호출");
        String memberId = request.getParameter("memberId");

        Member findMember = memberRepository.findById(
                Long.valueOf(memberId)).orElseThrow(() -> {
            throw new NoSuchMemberException("존재하지 않는 회원입니다.");
        });

        AuthType authType = findMember.getAuthType();

        if (!authType.equals(AuthType.ADMIN) && !authType.equals(AuthType.INT_EMPLOYEE)) {
            throw new NoAuthorityException("해당 기능을 사용할 수 없는 권한입니다. 관리자에게 문의하세요.");
        }

        return true;
    }
}
