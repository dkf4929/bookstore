package project.bookstore.configutration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
public class WebSecurityConfig {
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return null;
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http.authorizeRequests().antMatchers("/**").permitAll()
//                .anyRequest().authenticated()
//                .and().formLogin()
//                .permitAll()
//                .and()
//                .logout().permitAll();

//        return http.build();
//    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**", "/books/search");
//    }
}
