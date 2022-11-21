package project.bookstore.configutration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.bookstore.interceptor.AuthInterceptor;
import project.bookstore.repository.MemberRepository;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final MemberRepository memberRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(memberRepository))
                .order(1)
                .addPathPatterns(new String[]{"/books/**"})
                .excludePathPatterns("/books/search");
    }
}
