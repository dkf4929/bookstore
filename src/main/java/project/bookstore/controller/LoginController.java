package project.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.bookstore.service.CustomMemberDetailsService;
import project.bookstore.tokenmanager.JwtTokenProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final CustomMemberDetailsService memberDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> loginId) {
        List<String> list = new ArrayList<>();
        list.add("ADMIN");
        return jwtTokenProvider.createToken(loginId.get("loginId"), list);
    }
}
