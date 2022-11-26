package project.bookstore.configutration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.bookstore.filter.JwtAuthenticationFilter;
import project.bookstore.repository.MemberRepository;
import project.bookstore.service.CustomMemberDetailsService;
import project.bookstore.tokenmanager.JwtTokenProvider;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new CustomMemberDetailsService(memberRepository);
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
                http.httpBasic().disable()
                    .authorizeRequests()
                        .antMatchers("/members/**").hasRole("ADMIN")
                        .antMatchers("/order/**").hasRole("USER")
                        .antMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//                  .formLogin().usernameParameter("loginId");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
}
